package com.hezk.security.controller;

import org.springframework.stereotype.Component;

@Component
public class Person {

    public String say() {
        System.out.println("say");
        return "say";
    }
}
