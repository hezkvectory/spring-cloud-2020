package com.hezk.commons.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.apache.catalina.mapper.Mapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;

import java.util.concurrent.TimeUnit;

@Aspect
public class AutoMonitorAspect {

    @Autowired
    private MeterRegistry meterRegistry;

    @Pointcut("execution(* com.hezk.h2.mapper..*(..))")
    public void daoPointCut() {
    }

    @Around("daoPointCut()")
    public Object aroundDao(ProceedingJoinPoint pjp) throws Throwable {
        return this.aroundTimer(pjp);
    }

    @Around("execution(* com.hezk.feign.server.controller.IndexController.*(..))")
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        return this.aroundTimer(pjp);
    }

/*    @AfterThrowing(throwing = "ex", pointcut = "execution (* com.vipkid..*.client..* (..))")
    public void afterThrowingClient(JoinPoint jp, Throwable ex) {
        metricRegistry.counter("fail").incr(MetricRegistry.name(jp.getTarget().getClass().getSimpleName(), new String[]{jp.getSignature().getName(), "fail"}));
    }*/

    private Object aroundTimer(ProceedingJoinPoint pjp) throws Throwable {
        Class clazz = pjp.getSignature().getDeclaringType();
        String type = "other";
        if (AnnotatedElementUtils.isAnnotated(clazz, Controller.class)) {
            type = "controller";
        }
        String name = clazz.getSimpleName();
        Timer timer = meterRegistry.timer(type, "clazz", name, "method", pjp.getSignature().getName());
        long startTime = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            timer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
            timer.count();
        }
    }

}
