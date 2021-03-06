package com.hezk.standalone.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.hezk.h2.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;


@RequestMapping("/")
@RestController
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserMapper userMapper;

    @GetMapping({"/", "/index"})
    public String index() {
        System.out.println("IndexController.index classLoader: " + this.getClass().getClassLoader());

        LOGGER.trace("trace....");
        LOGGER.debug("debug....");
        LOGGER.info("info....");
        LOGGER.warn("warn....");
        LOGGER.error("error....");

        MBeanServer mserver = ManagementFactory.getPlatformMBeanServer();
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        LOGGER.info("Handling home");
        return "Hello World";
    }

    @GetMapping("/sleep10")
    public String sleep10(){
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
        }
        return "sleep10";
    }


    @GetMapping("/sleep100")
    public String sleep100(){
        try {
            userMapper.findById(1);
            Thread.sleep(100L);
        } catch (InterruptedException e) {
        }
        return "sleep100";
    }


    @GetMapping("/sleep200")
    public String sleep200(){
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
        }
        return "sleep200";
    }


    @GetMapping("/json")
    public Person json(){
        Person p = new Person();
        p.setUsername("h");
        p.setAge(1);
        return p;
    }


    static class Person{
        @SerializedName("_username")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("_redirect_url")
        String username;
        int age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getInfo(){
            return this.username + ":" + this.age;
        }
    }
}
