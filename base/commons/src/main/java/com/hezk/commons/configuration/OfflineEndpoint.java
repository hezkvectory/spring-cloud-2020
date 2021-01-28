package com.hezk.commons.configuration;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Endpoint(id = "offline")
public class OfflineEndpoint implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @ReadOperation
    public boolean offline() {
        AvailabilityChangeEvent.publish(this.applicationContext, ReadinessState.REFUSING_TRAFFIC);
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
