package com.hezk.aop.business.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Component
@Aspect
public class StudentBeforeAdvice {
    @Pointcut("execution(* com.hezk.aop.business.impl.StudentBImpl.*(..))")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object aroundIntercept(ProceedingJoinPoint pjp) throws Throwable {
        encrypt();
        Object retVal = pjp.proceed();
        decrypt();
        return retVal;
    }

    // 加密
    private void encrypt() {
        System.out.println("encrypt ...");
    }

    // 解密
    private void decrypt() {
        System.out.println("decrypt ...");
    }
}