package com.hezk.kill.controller;

import com.hezk.kill.enums.StatusCode;
import com.hezk.kill.mapper.RandomCodeMapper;
import com.hezk.kill.response.BaseResponse;
import com.hezk.kill.thread.CodeGenerateSnowThread;
import com.hezk.kill.thread.CodeGenerateThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("base")
public class BaseController {

    @Autowired
    private RandomCodeMapper randomCodeMapper;

    /**
     * 测试在高并发下多线程生成订单编号-传统的随机数生成方法
     */
    @GetMapping(value = "/code/generate/thread")
    public BaseResponse codeThread() {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 1000; i++) {
                executorService.execute(new CodeGenerateThread(randomCodeMapper));
            }
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }


    /**
     * 测试在高并发下多线程生成订单编号-雪花算法
     */
    @GetMapping(value = "/code/generate/thread/snow")
    public BaseResponse codeThreadSnowFlake() {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 10000; i++) {
                executorService.execute(new CodeGenerateSnowThread(randomCodeMapper));
            }
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }
}























