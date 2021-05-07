package com.hezk.aop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RetryService {

    private Logger logger = LoggerFactory.getLogger(RetryService.class);

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 2))
    public void devide(double a, double b) {
        logger.info("开始进行除法运算 " + LocalDateTime.now());
        if (b == 0) {
            throw new RuntimeException();
        }
        logger.info("{} / {} = {}", a, b, a / b);
    }

    @Recover
    public void recover() {
        logger.error("被除数不能为0 " + LocalDateTime.now());
    }

}