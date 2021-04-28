package com.hezk.hessian.configuration;

import com.hezk.hessian.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianConfig {

    @Autowired
    private IConfigService configService;

    @Bean(name = "/ConfigService")
    public HessianServiceExporter hessianServiceExporter() {
        HessianServiceExporter exporter = new HessianServiceExporter();

        exporter.setService(configService);
        exporter.setServiceInterface(IConfigService.class);

        return exporter;
    }
}