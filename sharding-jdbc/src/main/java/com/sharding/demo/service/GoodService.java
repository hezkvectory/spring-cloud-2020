package com.sharding.demo.service;

import com.sharding.demo.pojo.Goods;

public interface GoodService {
    Goods selectOneGoods(Long goodsId);

    String addGoods();
}
