//package com.hezk.feign.client;
//
//import com.hezk.commons.configuration.MeteorZonePreferenceServiceInstanceListSupplier;
//import io.micrometer.core.instrument.Meter;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.config.MeterFilter;
//import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
//import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
//import org.springframework.cloud.loadbalancer.core.DiscoveryClientServiceInstanceListSupplier;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//import java.time.Duration;
//
//@Configuration
//@LoadBalancerClients(defaultConfiguration = {CommonLoadBalancerConfig.class})
//public class WebAutoConfiguration {
//
//}