package com.hezk.kill.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("redis.config.host")
    String redisHost;

    @Value("spring.redis.password")
    String redisPassword;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(redisHost).setPassword(redisPassword);
        RedissonClient client = Redisson.create(config);
        return client;
    }
}