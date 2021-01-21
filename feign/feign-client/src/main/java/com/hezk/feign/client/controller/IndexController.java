package com.hezk.feign.client.controller;

import com.hezk.feign.client.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    @GetMapping({"/", "/index"})
    public String index() {
        LOGGER.info("IndexController index...");
        return indexService.index();
    }


    @GetMapping("/async")
    public String async() {
        LOGGER.info("IndexController index...");
        return indexService.async();
    }
}
