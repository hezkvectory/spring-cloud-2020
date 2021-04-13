package com.hezk.commons.h2.configuration;

import org.springframework.boot.actuate.autoconfigure.metrics.AutoTimeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "mybatis.metrics")
public class MybatisMetricsProperties {

    private boolean enabled = false;

    private String metricName = "mybatis.requests";

    @NestedConfigurationProperty
    private final AutoTimeProperties autotime = new AutoTimeProperties();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public AutoTimeProperties getAutotime() {
        return autotime;
    }
}