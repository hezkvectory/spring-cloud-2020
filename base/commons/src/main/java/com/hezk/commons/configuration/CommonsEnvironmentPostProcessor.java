package com.hezk.commons.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

public class CommonsEnvironmentPostProcessor implements EnvironmentPostProcessor {

    public static final String SOURCE_NAME_FIRST = "commons config";

    private final Map<String, Object> firstSource = new HashMap<>();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        //已装载，则中断
        if (propertySources.contains(SOURCE_NAME_FIRST)) {
            return;
        }

        MapPropertySource firstPropertySource = new MapPropertySource(SOURCE_NAME_FIRST, firstSource);
        propertySources.addFirst(firstPropertySource);

        firstSource.put("management.endpoint.prometheus.enabled", true);
        firstSource.put("management.endpoints.web.exposure.include", "*");

        firstSource.put("spring.zipkin.enabled", false);
        firstSource.put("spring.zipkin.base-url", "http://localhost:9411/");
        firstSource.put("spring.sleuth.sampler.probability", 1.0);

        firstSource.put("eureka.client.register-with-eureka", true);
        firstSource.put("eureka.client.fetch-registry", true);
        firstSource.put("eureka.client.service-url.defaultZone", "http://admin:admin@eureka.vipkid-qa.com.cn/eureka");

        firstSource.put("eureka.instance.prefer-ip-address", true);
        firstSource.put("eureka.client.registry-fetch-interval-seconds", 6);
        firstSource.put("eureka.client.filter-only-up-instances", true);
        firstSource.put("eureka.instance.lease-renewal-interval-in-seconds", 6);
        firstSource.put("eureka.instance.lease-expiration-duration-in-seconds", 18);
        firstSource.put("eureka.instance.instance-enabled-onit", false);
        firstSource.put("eureka.client.instance-info-replication-interval-seconds", 6);
        firstSource.put("eureka.client.initial-instance-info-replication-interval-seconds", 30);
//        firstSource.put("spring.cloud.loadbalancer.configurations", "meteor");

        firstSource.put("management.endpoint.health.probes.enabled", true);

        firstSource.put("eureka.client.healthcheck.enabled", true);
        firstSource.put("eureka.client.eureka-server-connect-timeout-seconds", 3);
        firstSource.put("eureka.client.eureka-server-read-timeout-seconds", 3);
        firstSource.put("eureka.client.eureka-server-total-connections", 128);
        firstSource.put("eureka.client.eureka-server-total-connections-per-host", 16);
        firstSource.put("eureka.client.eureka-connection-idle-timeout-seconds", 6);
        firstSource.put("eureka.client.should-enforce-registration-at-init", false);
        firstSource.put("eureka.client.should-unregister-on-shutdown", true);


        firstSource.put("management.health.defaults.enabled", false);
        firstSource.put("eureka.instance.initial-status", "starting");

        firstSource.put("management.endpoint.health.show-components", "ALWAYS");
        firstSource.put("management.health.check.enabled", true);

        firstSource.put("management.endpoint.health.group.readiness.include", "readinessState,check");
    }

}
