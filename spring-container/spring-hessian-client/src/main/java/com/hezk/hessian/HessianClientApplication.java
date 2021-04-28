package com.hezk.hessian;

import com.hezk.hessian.dto.Config;
import com.hezk.hessian.dto.ResultBean;
import com.hezk.hessian.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Collection;

@SpringBootApplication
public class HessianClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(HessianClientApplication.class, args);
    }

    @Component
    static class Runner implements ApplicationRunner {

        @Autowired
        IConfigService configService;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            ResultBean<Long> addResult = configService
                    .addConfig(Config.builder().name("配置项名称").value("配置项值").build());
            System.out.println(addResult);

            ResultBean<Collection<Config>> all = configService.getAll();
            System.out.println(all);

            // 把刚刚新建的删除掉
            ResultBean<Boolean> deleteResult = configService.delete(addResult.getData());
            System.out.println(deleteResult);
        }
    }

}
