package com.hezk.feign.client.service.impl;

import com.hezk.feign.client.controller.IndexController;
import com.hezk.feign.client.manager.IndexFeign;
import com.hezk.feign.client.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private IndexFeign indexFeign;

    @Override
    public String index() {
        LOGGER.info("IndexService index....");
//        async();

     /*   threadPoolTaskExecutor.execute(()->{
            LOGGER.info("thread IndexService index....");
            indexFeign.index();
        });*/

        return indexFeign.index();
    }

    @Async
    @Override
    public String async(){
        LOGGER.info("IndexService async ....");

        threadPoolTaskExecutor.execute(()->{
            LOGGER.info("IndexService async thread ....");
            indexFeign.index();
        });

        return indexFeign.index();
    }
}
