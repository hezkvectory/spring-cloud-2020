package com.hezk.standalone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/")
@RestController
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @GetMapping({"/", "/index"})
    public String index() {
        System.out.println("IndexController.index classLoader: " + this.getClass().getClassLoader());

        LOGGER.trace("trace....");
        LOGGER.debug("debug....");
        LOGGER.info("info....");
        LOGGER.warn("warn....");
        LOGGER.error("error....");
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


}
