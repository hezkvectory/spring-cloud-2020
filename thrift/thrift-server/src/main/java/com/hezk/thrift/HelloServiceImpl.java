package com.hezk.thrift;

import org.apache.thrift.TException;

public class HelloServiceImpl implements HelloWorldService.Iface {

    @Override
    public String sayHello(String username) throws TException {
        return "hello: " + username;
    }
}