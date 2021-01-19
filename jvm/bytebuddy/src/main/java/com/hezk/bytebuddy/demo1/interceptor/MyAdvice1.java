package com.hezk.bytebuddy.demo1.interceptor;

import net.bytebuddy.asm.Advice;

/**
 * 注意：实例方法使用@Advice.This注解，静态方法使用@Advice.Origin 两者不能混用
 */
public class MyAdvice1 {
    @Advice.OnMethodEnter()
    public static void exit(
            @Advice.Origin("#t") String className,
            @Advice.Origin("#m") String methodName,
            @Advice.AllArguments Object[] allParams,
            @Advice.FieldValue(value = "body", readOnly = false) String body
    ) {
        System.out.println("  ---MyAdvice-----methodName=" + methodName);
        body = "Hello Word";
    }

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(
            @Advice.Origin("#t") String className,
            @Advice.Origin("#m") String methodName,
            @Advice.AllArguments Object[] allParams,
            @Advice.Thrown Throwable t,
            @Advice.Return Object returnValue,
            @Advice.FieldValue(value = "body") String body) {
        System.out.println("  ----MyAdvice----returnValue=" + returnValue);
        System.out.println("body===================>" + body);
    }


}
