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
        firstSource.put("eureka.client.service-url.defaultZone", "http://localhost:7001/eureka");

//        firstSource.put("spring.cloud.loadbalancer.configurations", "meteor");

        firstSource.put("management.endpoint.health.probes.enabled", true);

        firstSource.put("eureka.client.healthcheck.enabled", true);

    }

}
