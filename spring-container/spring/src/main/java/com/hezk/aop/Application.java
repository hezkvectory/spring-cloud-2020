package com.hezk.aop;

import com.hezk.aop.service.impl.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Component;

@EnableRetry
//@EnableAsync
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@ComponentScan("com.hezk.aop")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        applicationContext.register(Application.class);
//        applicationContext.refresh();
//
//        StudentA studentA = applicationContext.getBean(StudentA.class);
//        System.out.println(studentA);
//
//
//        StudentB studentB = applicationContext.getBean(StudentB.class);
//        System.out.println(studentB);
////        System.out.println(AopUtils.getTargetClass(studentB));
////        System.out.println(studentB.getClass());
//        studentB.test();
    }

    @Component
    static class Runner implements ApplicationRunner {

        @Autowired
        private RetryService retryService;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            try {
                retryService.devide(1, 0);
            } catch (Exception e) {
            }
        }
    }

}
