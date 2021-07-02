package com.sharding.demo.service.impl;

import com.sharding.demo.mapper.GoodsMapper;
import com.sharding.demo.pojo.Goods;
import com.sharding.demo.service.GoodService;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.masterslave.route.engine.impl.MasterVisitedManager;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    @Transactional
    public Goods selectOneGoods(Long goodsId) {
//        MasterVisitedManager.setMasterVisited();
//        HintManager.getInstance().setMasterRouteOnly();
        return goodsMapper.selectOneGoods(goodsId);
    }

    /**
     * 加@Transactional注解，事物生效
     *
     * @return
     */
    @Override
    @Transactional
    public String addGoods() {
        goodsMapper.selectOneGoods(3L);
        Goods goods = new Goods();
        goods.setGoodsName("goods2");
        goods.setStock(10);
        goodsMapper.save(goods);
        int i = 1 / 0;
        return "success";
    }
}
