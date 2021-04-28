package com.hezk.kill.thread;


import com.hezk.kill.domain.RandomCode;
import com.hezk.kill.mapper.RandomCodeMapper;
import com.hezk.kill.utils.SnowFlake;

public class CodeGenerateSnowThread implements Runnable {

    private static final SnowFlake SNOW_FLAKE = new SnowFlake(2, 3);

    private RandomCodeMapper randomCodeMapper;

    public CodeGenerateSnowThread(RandomCodeMapper randomCodeMapper) {
        this.randomCodeMapper = randomCodeMapper;
    }

    @Override
    public void run() {
        RandomCode entity = new RandomCode();
        entity.setCode(String.valueOf(SNOW_FLAKE.nextId()));
        randomCodeMapper.insertSelective(entity);
    }
}