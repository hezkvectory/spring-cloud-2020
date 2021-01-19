package com.hezk.bytebuddy.demo1.interceptor;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

/**
 * 注意：实例方法使用@Advice.This注解，静态方法使用@Advice.Origin 两者不能混用
 */
public class StaticMethodSpendAdviceInterceptor {
    @Advice.OnMethodEnter()
    public static void enter(@Advice.Local("startTime") Long startTime, @Advice.Origin Method m, @Advice.Origin Class clz) throws Throwable {
        System.out.println("---[BEGIN] InstanceMethodSpendAdviceInterceptor---" + clz.getName() + "." + m.getName());
        System.out.println("  ----------" + clz.getName());
        startTime = System.currentTimeMillis();
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Local("startTime") Long startTime) throws Throwable {
        System.out.println("  ==============>spend=" + (System.currentTimeMillis() - startTime));
        System.out.println("---[END] InstanceMethodSpendAdviceInterceptor");
    }


}