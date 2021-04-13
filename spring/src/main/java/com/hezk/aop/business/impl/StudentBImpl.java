package com.hezk.aop.business.impl;

import com.hezk.aop.business.StudentB;
import com.hezk.aop.business.StudentC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class StudentBImpl implements StudentB {
    @Autowired
    private StudentC studentC;

    @Override
    @Async
    public void test() {
        System.out.println("----" + Thread.currentThread().getName());
    }
}
