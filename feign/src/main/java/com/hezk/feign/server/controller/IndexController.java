package com.hezk.feign.server.controller;

import com.hezk.h2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class IndexController {

    @javax.annotation.Resource
    private UserMapper userMapper;

    @Value("classpath:init.txt")
    private Resource resource;

    @GetMapping({"/", "/index"})
    public String index() throws IOException {
        userMapper.findById(1);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
//        return new BufferedReader(new EncodedResource(resource, "UTF-8").getReader()).readLine();
    }
}
