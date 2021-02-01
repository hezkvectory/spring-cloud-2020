package com.hezk.aop.proxy;

import com.hezk.aop.service.ChromeBrowser;
import org.springframework.context.annotation.*;

@ComponentScan("com.hezk.aop")
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ProxyFactoryAnnotationTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ProxyFactoryAnnotationTest.class);
        applicationContext.refresh();
        
        ChromeBrowser chromeBrowser = applicationContext.getBean(ChromeBrowser.class);
        chromeBrowser.visitInternet();
    }

    @Bean
    public ChromeBrowser chromeBrowser() {
        return new ChromeBrowser();
    }

}
