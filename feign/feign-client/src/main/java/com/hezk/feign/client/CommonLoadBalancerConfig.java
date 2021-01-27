//package com.hezk.feign.client;
//
//import com.hezk.commons.configuration.MeteorZonePreferenceServiceInstanceListSupplier;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.loadbalancer.core.DiscoveryClientServiceInstanceListSupplier;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.annotation.Order;
//
//public class CommonLoadBalancerConfig {
//
//
//    @Bean
//    @Order(Integer.MIN_VALUE)
//    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.configurations", havingValue = "meteor")
//    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
//            ConfigurableApplicationContext context,
//            DiscoveryClient discoveryClient) {
//
//        ServiceInstanceListSupplier delegate = new MeteorZonePreferenceServiceInstanceListSupplier(
//                new DiscoveryClientServiceInstanceListSupplier(discoveryClient, context.getEnvironment())
//        );
//
//        return delegate;
//
////        return ServiceInstanceListSupplier.builder()
////                .withBase(delegate)
////                .build(context);
//    }
//
//
//}
