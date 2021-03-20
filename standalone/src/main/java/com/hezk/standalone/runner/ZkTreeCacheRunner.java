package com.hezk.standalone.runner;

import com.hezk.standalone.zk.ZkClusterManager;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
public class ZkTreeCacheRunner implements ApplicationRunner {

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