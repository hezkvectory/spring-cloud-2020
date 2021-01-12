package com.hezk.feign.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class IndexController {

    @Value("classpath:init.txt")
    private Resource resource;

    @GetMapping({"/", "/index"})
    public String index() throws IOException {
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
//        return new BufferedReader(new EncodedResource(resource, "UTF-8").getReader()).readLine();
    }
}
