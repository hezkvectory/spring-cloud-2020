package com.hezk.commons.configuration;

import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@LoadBalancerClients(defaultConfiguration = {CommonLoadBalancerConfig.class})
public class WebAutoConfiguration {

    @Bean
    public OnlineEndpoint onlineEndpoint() {
        return new OnlineEndpoint();
    }

    @Bean
    public OfflineEndpoint offlineEndpoint() {
        return new OfflineEndpoint();
    }

    @Bean
    @ConditionalOnEnabledHealthIndicator("check")
    public CheckHealthIndicator checkHealthIndicator() {
        return new CheckHealthIndicator();
    }

}