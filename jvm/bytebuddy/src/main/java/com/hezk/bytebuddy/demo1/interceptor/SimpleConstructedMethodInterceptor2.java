package com.hezk.bytebuddy.demo1.interceptor;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class SimpleConstructedMethodInterceptor2 {
    @RuntimeType
    public Object intercept(@This Object obj,
                            @AllArguments Object[] arguments) throws Throwable {
        System.out.println("---[BEGIN] SimpleConstructedMethodInterceptor2");
        Object ret = null;
        try {
            System.out.println("    arguments number = " + arguments.length);
        } catch (Throwable t) {
            throw t;
        } finally {
            System.out.println("---[END] SimpleConstructedMethodInterceptor2");
        }
        return ret;
    }
}
