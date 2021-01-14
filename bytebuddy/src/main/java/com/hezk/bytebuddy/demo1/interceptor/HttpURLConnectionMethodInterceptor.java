package com.hezk.bytebuddy.demo1.interceptor;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.util.concurrent.Callable;

public class HttpURLConnectionMethodInterceptor {
    @RuntimeType
    public static String intercept(@This Object obj, @SuperCall Callable<String> zuper) throws Throwable {
        System.out.println("---[BEGIN] HttpURLConnectionMethodInterceptor:" + System.currentTimeMillis());
        String ret = null;
        try {
            System.out.println(obj + ",,,,");
            ret = zuper.call();
            System.out.println(ret);
        } catch (Throwable t) {
            throw t;
        } finally {
            System.out.println("---[END] HttpURLConnectionMethodInterceptor:" + System.currentTimeMillis());
        }
        return ret;
    }
}
