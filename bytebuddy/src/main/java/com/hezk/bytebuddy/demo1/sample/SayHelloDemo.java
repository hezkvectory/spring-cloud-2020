package com.hezk.bytebuddy.demo1.sample;

public class SayHelloDemo {
    public String context = " is a cat!";

    public String sayHello(String name) {
        String say = "Hello " + name + " !";
        System.out.println(say);
        return say;
    }

}
