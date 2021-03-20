package com.hezk.standalone.configuration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public ApplicationRunner runner(){
        return (arguments)->{
            System.out.println("WebConfiguration.runner");
        };
    }
}
