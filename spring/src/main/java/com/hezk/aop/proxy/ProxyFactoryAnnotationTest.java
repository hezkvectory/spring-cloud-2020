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
        chromeBrowser.hello();

//        ChromeBrowser2 chromeBrowser2 = applicationContext.getBean(ChromeBrowser2.class);
//        chromeBrowser2.hello2();

    }

    @Bean
    public ChromeBrowser chromeBrowser() {
        return new ChromeBrowser();
    }

}
