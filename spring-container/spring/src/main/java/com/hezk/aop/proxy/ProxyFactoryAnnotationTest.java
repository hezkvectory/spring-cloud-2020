package com.hezk.aop.proxy;

import com.hezk.aop.service.ChromeBrowser;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
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


        ChromeBrowser chromeBrowser1 = applicationContext.getBean(ChromeBrowser.class);
        chromeBrowser1.hello();

        System.out.println(chromeBrowser.getClass());
        System.out.println(chromeBrowser1.getClass());

        System.out.println(chromeBrowser);
        System.out.println(chromeBrowser1);

        System.out.println(AopUtils.getTargetClass(chromeBrowser));
        System.out.println(AopUtils.getTargetClass(chromeBrowser1));

//        ChromeBrowser2 chromeBrowser2 = applicationContext.getBean(ChromeBrowser2.class);
//        chromeBrowser2.hello2();

    }

    @Bean
    @Scope("prototype")
    public ChromeBrowser chromeBrowser() {
        return new ChromeBrowser();
    }

}
