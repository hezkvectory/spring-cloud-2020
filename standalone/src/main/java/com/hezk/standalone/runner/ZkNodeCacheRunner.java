package com.hezk.standalone.runner;

import com.hezk.standalone.zk.ZkClusterManager;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
public class ZkNodeCacheRunner implements ApplicationRunner {

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
