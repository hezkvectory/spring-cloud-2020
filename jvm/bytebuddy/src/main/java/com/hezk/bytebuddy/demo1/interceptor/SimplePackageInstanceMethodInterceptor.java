package com.hezk.bytebuddy.demo1.interceptor;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SimplePackageInstanceMethodInterceptor {
    @RuntimeType
    public Object intercept(@This Object obj,
                            @AllArguments Object[] arguments,
                            @SuperCall Callable<?> zuper,
                            @Origin Method method) throws Throwable {
        System.out.println("---[BEGIN]--- SimplePackageInstanceMethodInterceptor");
        Object ret;
        try {
            System.out.println("    class name = " + obj.getClass().getName());
            System.out.println("    method name = " + method.getName());
            ret = zuper.call();
        } catch (Throwable t) {
            throw t;
        } finally {
            System.out.println("---[END]--- SimplePackageInstanceMethodInterceptor");
        }
        return ret;
    }
}