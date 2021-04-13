package com.hezk.aop;

import com.hezk.aop.business.StudentA;
import com.hezk.aop.business.StudentB;
import com.hezk.aop.service.ChromeBrowser;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan("com.hezk.aop.business")
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Application.class);
        applicationContext.refresh();

        StudentA studentA = applicationContext.getBean(StudentA.class);
        System.out.println(studentA);


        StudentB studentB = applicationContext.getBean(StudentB.class);
        System.out.println(studentB);
//        System.out.println(AopUtils.getTargetClass(studentB));
//        System.out.println(studentB.getClass());
        studentB.test();
    }
}
