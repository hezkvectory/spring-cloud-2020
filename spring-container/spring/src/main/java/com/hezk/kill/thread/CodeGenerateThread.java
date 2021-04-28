package com.hezk.kill.thread;

import com.hezk.kill.domain.RandomCode;
import com.hezk.kill.mapper.RandomCodeMapper;
import com.hezk.kill.utils.RandomUtil;

public class CodeGenerateThread implements Runnable{

    private RandomCodeMapper randomCodeMapper;

    public CodeGenerateThread(RandomCodeMapper randomCodeMapper) {
        this.randomCodeMapper = randomCodeMapper;
    }

    @Override
    public void run() {
        RandomCode entity=new RandomCode();
        entity.setCode(RandomUtil.generateOrderCode());
        randomCodeMapper.insertSelective(entity);
    }
}