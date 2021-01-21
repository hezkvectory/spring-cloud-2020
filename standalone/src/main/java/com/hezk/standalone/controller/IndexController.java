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

        LOGGER.trace("trace....");
        LOGGER.debug("debug....");
        LOGGER.info("info....");
        LOGGER.warn("warn....");
        LOGGER.error("error....");
        return "index";
    }


    @RequestMapping("/home")
    public String home() {
        LOGGER.info("Handling home");
        return "Hello World";
    }

}
