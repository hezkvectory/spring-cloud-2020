package com.hezk.kill.controller;

import com.hezk.kill.domain.ItemKill;
import com.hezk.kill.response.Result;
import com.hezk.kill.response.ResultBuilder;
import com.hezk.kill.service.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    private static final String prefix = "item";

    @Autowired
    private IItemService itemService;

    /**
     * 获取商品列表
     */
    @ResponseBody
    @GetMapping(value = {"/", "/index", prefix + "/list", prefix + "/index.html"})
    public Result list() {
        try {
            //获取待秒杀商品列表
            List<ItemKill> list = itemService.getKillItems();
            log.info("获取待秒杀商品列表-数据：{}", list);
            return ResultBuilder.success().putData("list", list);
        } catch (Exception e) {
            log.error("获取待秒杀商品列表-发生异常：", e.fillInStackTrace());
        }
        return ResultBuilder.systemError();
    }

    /**
     * 获取待秒杀商品的详情
     */
    @ResponseBody
    @GetMapping(value = prefix + "/detail/{id}")
    public Result detail(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResultBuilder.parameterError();
        }
        try {
            ItemKill detail = itemService.getKillDetail(id);

            return ResultBuilder.success().putData("detail", detail);
        } catch (Exception e) {
            log.error("获取待秒杀商品的详情-发生异常：id={}", id, e.fillInStackTrace());
        }
        return ResultBuilder.parameterError();
    }
}





























