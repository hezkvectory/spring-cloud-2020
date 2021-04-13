package com.hezk.standalone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@MapperScan(basePackages = {"com.hezk.h2.mapper"})
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class},
        scanBasePackages = {"com.hezk.standalone", "com.hezk.h2"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
