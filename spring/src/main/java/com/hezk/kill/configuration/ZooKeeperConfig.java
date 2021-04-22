package com.hezk.kill.configuration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZooKeeperConfig {

    @Value("${zk.host}")
    String zkHost;

    @Value("${zk.namespace}")
    String zkNamespace;

    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(zkHost)
                .namespace(zkNamespace)
                //重试策略
                .retryPolicy(new RetryNTimes(5, 1000))
                .build();
        curatorFramework.start();
        return curatorFramework;
    }
}
































