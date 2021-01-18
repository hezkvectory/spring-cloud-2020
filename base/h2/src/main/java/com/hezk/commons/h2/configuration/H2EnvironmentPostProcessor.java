package com.hezk.commons.h2.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

public class H2EnvironmentPostProcessor implements EnvironmentPostProcessor {

    public static final String SOURCE_NAME_FIRST = "h2 config";

    private final Map<String, Object> firstSource = new HashMap<>();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        if (propertySources.contains(SOURCE_NAME_FIRST)) {
            return;
        }

        MapPropertySource firstPropertySource = new MapPropertySource(SOURCE_NAME_FIRST, firstSource);
        propertySources.addFirst(firstPropertySource);

        firstSource.put("spring.datasource.driver-class-name", "org.h2.Driver");
        firstSource.put("spring.datasource.schema", "classpath:db/schema.sql");
        firstSource.put("spring.datasource.data", "classpath:db/data.sql");
        firstSource.put("spring.datasource.url", "jdbc:h2:mem:test");
        firstSource.put("spring.datasource.username", "sa");
        firstSource.put("spring.datasource.password", "sa");
        firstSource.put("spring.h2.console.enabled", "true");
    }

}
