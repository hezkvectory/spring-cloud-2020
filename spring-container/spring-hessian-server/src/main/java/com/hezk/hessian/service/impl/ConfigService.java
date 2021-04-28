package com.hezk.hessian.service.impl;


import com.hezk.hessian.dto.Config;
import com.hezk.hessian.dto.ResultBean;
import com.hezk.hessian.service.IConfigService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ConfigService implements IConfigService {

    private final ConcurrentSkipListMap<Long, Config> configs = new ConcurrentSkipListMap<Long, Config>();

    private static final AtomicLong idSequence = new AtomicLong(1000L);

    @Override
    public ResultBean<Long> addConfig(Config config) {
        System.out.println("addConfig：" + config);

        long id = idSequence.incrementAndGet();

        config.setId(id);
        configs.put(id, config);

        return new ResultBean<>(id);
    }

    @Override
    public ResultBean<Collection<Config>> getAll() {
        System.out.println("GetAll：=======打印一个日志，后面会用到========");
        return new ResultBean<>(configs.values());
    }

    @Override
    public ResultBean<Boolean> delete(long id) {
        System.out.println("Delete：" + id);
        return new ResultBean<>(configs.remove(id) != null);
    }

}