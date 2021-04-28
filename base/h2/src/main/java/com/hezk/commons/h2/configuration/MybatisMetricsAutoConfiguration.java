package com.hezk.commons.h2.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class,
        SimpleMetricsExportAutoConfiguration.class})
@ConditionalOnBean(MeterRegistry.class)
@EnableConfigurationProperties(MybatisMetricsProperties.class)
public class MybatisMetricsAutoConfiguration {

    private MybatisMetricsProperties mybatisMetricsProperties;

    public MybatisMetricsAutoConfiguration(MybatisMetricsProperties mybatisMetricsProperties) {
        this.mybatisMetricsProperties = mybatisMetricsProperties;
    }

    @Bean
    @ConditionalOnProperty(name = "mybatis.metrics.enabled", havingValue = "true")
    public MybatisMetricsInterceptor mybatisProfilerPlugin(MeterRegistry registry) {
        return new MybatisMetricsInterceptor(registry, mybatisMetricsProperties.getMetricName(), mybatisMetricsProperties.getAutotime());
    }

}
