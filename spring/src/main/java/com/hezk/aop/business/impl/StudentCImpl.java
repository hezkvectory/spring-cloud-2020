package com.hezk.aop.business.impl;

import com.hezk.aop.business.StudentB;
import com.hezk.aop.business.StudentC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCImpl implements StudentC {
//    @Autowired
//    private StudentB studentB;

    @Override
    public void test() {

    }
}
