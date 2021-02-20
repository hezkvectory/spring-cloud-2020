package com.hezk.standalone.zk;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ZkClusterManager {

    private CuratorFramework client;
    private TreeCache cache;
    private NodeCache nodeCache;

    private boolean closed = false;

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkClusterManager.class);

    private ExecutorService executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() / 2, Runtime.getRuntime().availableProcessors() / 2, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("TreeCache-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());

    public TreeCache getCache() {
        return cache;
    }

    /**
     * 执行器节点
     */
    public static final String SCHEDULE_PATH = "/schedulenodes";
    public static final String SCHEDULE_IP_PATH = "/schedulenodes/%s";
    /**
     * 事件节点 启动/停止调度
     */
    public static final String EVENT_PATH = "/event";
    public static final String EMPTY = "";

    /**
     * 任务节点
     */
    public static final String JOB_PATH = "/jobs";
    public static final String JOB_IPLIST_PATH = "/jobs/%s/ips";
    public static final String JOB_IPNODE_PATH = "/jobs/%s/ips/%s";

    public static final String JOB_SCHEDULE_RESULT_PATH_POSTFIX = "/schedule/result";
    public static final String JOB_SCHEDULE_RESULT_PATH = JOB_PATH + "/%s" + JOB_SCHEDULE_RESULT_PATH_POSTFIX;
    public static final String JOB_STATUS_PATH = "/jobs/%s/status";
    public static final String JOB_FAILOVER_NODE = "/jobs/%s/failover/node";
    public static final String JOB_SCHEDULE_FAIL_RETRY_PATH_E = "/jobs/%s/schedule/retry";
    /**
     * 分布式锁
     */
    public static final String MONITOR_LOCK_MINUTE = "/lock/minute/";

    @Autowired
    private InetUtils inet;

    @PostConstruct
    public void init() {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3, 3000))
                .namespace("hezk");
        builder.sessionTimeoutMs(15000);
        builder.connectionTimeoutMs(3000);
        try {
            client = builder.build();
            client.start();
            client.blockUntilConnected();
            LOGGER.info("init ZKNodes");
            initZkNodes();
            LOGGER.info("start treeCache");
            cacheData();
            LOGGER.info("init ZkClusterManager completed");
        } catch (Exception e) {
            LOGGER.info("init ZkClusterManager failed");
        }
    }

    @PreDestroy
    public void close() {
        if (closed) {
            return;
        }
        LOGGER.info("closeing zk-client");
        String address = inet.findFirstNonLoopbackAddress().getHostAddress();
        try {
            LOGGER.info("remove SCHEDULE_IP_PATH:{}", String.format(SCHEDULE_IP_PATH, address));
            //zk自动移除机制有延迟 手动关闭
            remove(String.format(SCHEDULE_IP_PATH, address));
        } catch (Exception e) {
            LOGGER.error("remove SCHEDULE_IP_PATH:{} error", String.format(SCHEDULE_IP_PATH, address), e);
        } finally {
            if (null != cache) {
                cache.close();
            }
            if (null != nodeCache) {
                try {
                    nodeCache.close();
                } catch (IOException e) {
                    LOGGER.error("close nodeCache error", e);
                }
            }
            CloseableUtils.closeQuietly(client);
            closed = true;
        }

    }

    private void initZkNodes() throws Exception {
        persist(SCHEDULE_PATH, EMPTY);
        persist(EVENT_PATH, EMPTY);
        persist(JOB_PATH, EMPTY);
    }

    private void cacheData() throws Exception {
        cache = TreeCache.newBuilder(client, "/")
                .setExecutor(executor)
                .build();
        nodeCache = new NodeCache(client, ZkClusterManager.EVENT_PATH);
        cache.start();
        nodeCache.start(true);
    }

    public void registry(String address) {
        String localNodePath = ZkClusterManager.SCHEDULE_PATH + "/" + address;
        persistEphemeral(localNodePath, address, true);
        LOGGER.info("Schedule Node : {} registry to ZK successfully.", address);
    }

    /**
     * 更新ZK 的key
     *
     * @param key
     * @param value
     */
    public void persist(final String key, final String value) {
        try {
            if (!isExisted(key)) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath
                        (key, value.getBytes(Charset.forName("utf-8")));
            } else {
                update(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isExisted(final String key) {

        try {
            return client.checkExists().forPath(key) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void update(final String key, final String value) {
        try {
            client.inTransaction().check().forPath(key).and().setData().forPath(key, value
                    .getBytes(Charset.forName("UTF-8"))).and().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void waitForCacheClose() {
        try {
            Thread.sleep(500L);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public String get(final String key) {
        if (null == cache) {
            return null;
        }
        ChildData resultIncache = cache.getCurrentData(key);
        if (null != resultIncache) {
            return null == resultIncache.getData() ? null : new String(resultIncache.getData(), Charset.forName("UTF-8"));
        }
        return null;
    }

    public String getFromNodeCache() {
        if (null == nodeCache) {
            return null;
        }
        ChildData resultIncache = nodeCache.getCurrentData();
        if (null != resultIncache) {
            return null == resultIncache.getData() ? null : new String(resultIncache.getData(), Charset.forName("UTF-8"));
        }
        return null;
    }

    public List<String> getScheduleNodes() {
        if (null == cache) {
            return null;
        }
        Set<String> returnValue = new HashSet<String>();
        Map<String, ChildData> dataMap = cache.getCurrentChildren(SCHEDULE_PATH);
        Collection<ChildData> values = dataMap.values();

        for (ChildData data : values) {
            returnValue.add(new String(data.getData(), Charset.forName("UTF-8")));
        }

        return new ArrayList<>(returnValue);
    }

    public String getDirectly(final String key) throws InterruptedException, IOException, KeeperException {

        try {
            return new String(client.getData().forPath(key), Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getChildrenKeys(final String key) {
        List<String> values = new ArrayList<String>();
        try {
            if (isExisted(key)) {
                values = client.getChildren().forPath(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }


    public void persistEphemeral(final String key, final String value) {
        try {
            if (isExisted(key)) {
                client.delete().deletingChildrenIfNeeded().forPath(key);
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key, value.getBytes(Charset.forName("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void persistEphemeral(String key, String value, boolean overwrite) {
        try {
            if (overwrite) {
                persistEphemeral(key, value);
            } else {
                if (!isExisted(key)) {
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key, value.getBytes(Charset.forName("UTF-8")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createEphemeral(String key, String value) throws Exception {
        return client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key, value.getBytes(Charset.forName("UTF-8")));
    }

    public void persistEphemeralSequential(final String key) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(final String key) throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath(key);
    }

    public Object getRawClient() {
        return client;
    }


    public void addConnectionStateListener(final ConnectionStateListener listener) {
        client.getConnectionStateListenable().addListener(listener);
    }


    public void addDataListener(final TreeCacheListener listener) {
        cache.getListenable().addListener(listener);
    }

    public void addNodeListener(final NodeCacheListener listener) {
        nodeCache.getListenable().addListener(listener);
    }

    public void removeDataListener(final TreeCacheListener listener) {
        cache.getListenable().removeListener(listener);
    }


    public boolean isJobEventPath(String path) {
        return path != null && path.startsWith(EVENT_PATH);
    }

    public boolean isScheculePath(String path) {
        return path != null && path.startsWith(SCHEDULE_PATH);
    }

    public boolean isJobStatusPath(String path) {
        return path != null && path.contains("/jobs") && path.contains("/status");
    }

    public boolean isScheculeJobRunPath(String path) {
        return path != null && path.contains(JOB_PATH) && path.contains("/schedule/run");
    }

    public boolean isScheduleJobResultPath(String path) {
        return path != null && path.endsWith(JOB_SCHEDULE_RESULT_PATH_POSTFIX);
    }

    public InterProcessMutex tryLock(String path) {
        InterProcessMutex mutex = new InterProcessMutex(client, MONITOR_LOCK_MINUTE + path);
        return mutex;
    }
}
