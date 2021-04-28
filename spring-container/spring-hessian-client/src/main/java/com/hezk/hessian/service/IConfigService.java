package com.hezk.hessian.service;

import com.hezk.hessian.dto.Config;
import com.hezk.hessian.dto.ResultBean;

import java.util.Collection;

public interface IConfigService {

    public ResultBean<Long> addConfig(Config config);

    public ResultBean<Collection<Config>> getAll();

    public ResultBean<Boolean> delete(long id);

}
