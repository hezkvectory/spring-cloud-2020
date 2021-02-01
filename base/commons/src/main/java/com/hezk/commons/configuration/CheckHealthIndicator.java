package com.hezk.commons.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.availability.ApplicationAvailabilityBean;
import org.springframework.boot.availability.ReadinessState;

public class CheckHealthIndicator extends AbstractHealthIndicator {

    @Autowired
    private ApplicationAvailabilityBean applicationAvailabilityBean;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        ReadinessState readinessState =  applicationAvailabilityBean.getState(ReadinessState.class);
        if (readinessState == ReadinessState.ACCEPTING_TRAFFIC) {
            builder.up();
        } else {
            builder.down();
        }
    }
}
