package com.hezk.commons.configuration;

import com.hezk.commons.metrics.AutoMonitorAspect;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

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

//    @Bean
//    public AutoMonitorAspect autoMonitorAspect() {
//        return new AutoMonitorAspect();
//    }

    @Bean
    public MeterFilter metricsCommonTagsFilter() {
        return new MeterFilter() {
            @Override
            public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
                if (id.getType() == Meter.Type.TIMER) {
                    return DistributionStatisticConfig.builder()
                            .percentilesHistogram(true)
                            .percentiles(0.5, 0.90, 0.95, 0.99)
                            .serviceLevelObjectives(Duration.ofMillis(100).toNanos(),
                                    Duration.ofMillis(200).toNanos(),
                                    Duration.ofSeconds(1).toNanos(),
                                    Duration.ofSeconds(5).toNanos())
                            .minimumExpectedValue((double) Duration.ofMillis(20).toNanos())
                            .maximumExpectedValue((double) Duration.ofSeconds(5).toNanos())
                            .build()
                            .merge(config);
                } else {
                    return config;
                }
            }
        };
    }
}