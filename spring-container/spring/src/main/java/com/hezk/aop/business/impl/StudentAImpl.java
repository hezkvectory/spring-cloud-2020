package com.hezk.aop.business.impl;

import com.hezk.aop.business.StudentA;
import com.hezk.aop.business.StudentB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentAImpl implements StudentA {

    @Autowired
    private StudentB studentB;
}
