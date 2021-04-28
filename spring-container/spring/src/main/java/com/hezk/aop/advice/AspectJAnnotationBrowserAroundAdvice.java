package com.hezk.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectJAnnotationBrowserAroundAdvice {

    @Pointcut("execution(* com.hezk.aop.service.ChromeBrowser.*(..))")
    private void pointcut() {
    }

    @Pointcut("execution(protected * com.hezk.aop.proxy.ChromeBrowser2.*(..))")
    private void pointcut2() {
    }

    @Around(value = "pointcut() || pointcut2()")
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