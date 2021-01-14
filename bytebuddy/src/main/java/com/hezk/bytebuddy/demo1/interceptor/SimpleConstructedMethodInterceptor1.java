package com.hezk.bytebuddy.demo1.interceptor;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class SimpleConstructedMethodInterceptor1 {
    @RuntimeType
    public Object intercept(@This Object obj,
                            @AllArguments Object[] allArguments) throws Throwable {
        System.out.println("---[BEGIN] SimpleConstructedMethodInterceptor1");
        Object ret = null;
        try {
            System.out.println("    allArguments number = " + allArguments.length);
        } catch (Throwable t) {
            throw t;
        } finally {
            System.out.println("---[END] SimpleConstructedMethodInterceptor1");
        }
        return ret;
    }
}
