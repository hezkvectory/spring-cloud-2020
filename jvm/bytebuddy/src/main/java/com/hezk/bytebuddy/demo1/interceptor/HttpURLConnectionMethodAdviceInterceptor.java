package com.hezk.bytebuddy.demo1.interceptor;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

public class HttpURLConnectionMethodAdviceInterceptor {
    //jdk的类，没法设置inline = false，inlined没法debug
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Local("start") long start, @Advice.Origin Method m, @Advice.This Object ths) throws Throwable {
        start = System.currentTimeMillis();
        System.out.println("---[BEGIN] HttpURLConnectionMethodAdviceInterceptor, start:" + start);
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Local("start") long start) throws Throwable {
        System.out.println("---[END] HttpURLConnectionMethodAdviceInterceptor, start:" + start);
    }
}