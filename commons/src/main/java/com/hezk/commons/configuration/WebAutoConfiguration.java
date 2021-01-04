package com.hezk.commons.configuration;

import com.hezk.commons.filter.ProfilerHttpFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "login.default.filter.enabled", havingValue = "true")
    public FilterRegistrationBean meteorLoginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        ProfilerHttpFilter filter = new ProfilerHttpFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("profilerHttpFilter");
        registration.setOrder(filter.getOrder());
        return registration;
    }

}
