package com.hezk.commons.configuration;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.DelegatingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class MeteorZonePreferenceServiceInstanceListSupplier extends DelegatingServiceInstanceListSupplier {

    public MeteorZonePreferenceServiceInstanceListSupplier(ServiceInstanceListSupplier delegate) {
        super(delegate);
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return getDelegate().get().map(this::filteredByZone);
    }

    private List<ServiceInstance> filteredByZone(List<ServiceInstance> serviceInstances) {
        List<ServiceInstance> filteredInstances = new ArrayList<>();
        for (ServiceInstance serviceInstance : serviceInstances) {
            filteredInstances.add(serviceInstance);
        }
        if (filteredInstances.size() > 0) {
            return filteredInstances;
        }
        return serviceInstances;
    }

}
