package com.hezk.standalone;

import com.hezk.standalone.zk.ZkClusterManager;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Component
    static class ZkTreeCacheRunner implements ApplicationRunner {

        private final String TAG = "调试# ";

        @Autowired
        private ZkClusterManager zkClusterManager;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            CuratorFramework client = zkClusterManager.getClient();
            TreeCache treeCache = new TreeCache(client, "/cloud");
            treeCache.getListenable().addListener(new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                    try {
                        System.out.println("=============");
                        System.out.println(TAG + "类型:" + event.getType().name());
                        System.out.println(TAG + "路径:" + event.getData().getPath());
                        System.out.println(TAG + "数据:" + new String(event.getData().getData(), "UTF-8"));

                        System.out.println(TAG + "ZooKeeper:" + client.getZookeeperClient().getZooKeeper().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            treeCache.start();

        }
    }

    @Component
    static class ZkNodeCacheRunner implements ApplicationRunner {

        private final String TAG = "调试2# ";

        @Autowired
        private ZkClusterManager zkClusterManager;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            CuratorFramework client = zkClusterManager.getClient();
            NodeCache nodeCache = new NodeCache(client, "/cloud/d");
            nodeCache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData data = nodeCache.getCurrentData();
                    if (data != null) {
                        System.out.println(TAG + "子路径:" + data.getPath());
                        System.out.println(TAG + "子数据:" + new String(data.getData(), "UTF-8"));
                    } else {
                        System.out.println("节点删除");
                    }
                }
            });
            nodeCache.start();
        }
    }

    //    @Component
    static class StartListener implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("StartListener.run begin......");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("StartListener.run end......");
        }
    }

    //    @Component
    static class CloseListener implements ApplicationListener<ContextClosedEvent> {

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            System.out.println("CloseListener.onApplicationEvent begin......");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("CloseListener.onApplicationEvent end......");
        }
    }
}
