package com.sharding.demo.controller;

import com.sharding.demo.mapper.GoodsMapper;
import com.sharding.demo.pojo.Goods;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    @Resource
    private GoodsMapper goodsMapper;

    //更新商品库存 参数:商品id,增加的库存数量
    @RequestMapping("/goodsstock/{goodsId}/{count}")
    @ResponseBody
    public String goodsStock(@PathVariable Long goodsId,
                             @PathVariable int count) {
        int res = goodsMapper.updateGoodsStock(goodsId, count);
        System.out.println("res:" + res);
        if (res > 0) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }

    //商品详情 参数:商品id
    @GetMapping("/goodsinfo")
    @ResponseBody
    public Goods goodsInfo(@RequestParam(value = "goodsid", defaultValue = "0") Long goodsId) {
        Goods goods = goodsMapper.selectOneGoods(goodsId);
        return goods;
    }
}
