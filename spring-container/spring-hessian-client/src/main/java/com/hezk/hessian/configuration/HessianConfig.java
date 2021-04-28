package com.hezk.hessian.configuration;

import com.hezk.hessian.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianConfig {

    @Bean
    public HessianProxyFactoryBean helloClient() {
        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
        factory.setServiceUrl("http://localhost:8080/ConfigService");
        factory.setServiceInterface(IConfigService.class);
        return factory;
    }

}