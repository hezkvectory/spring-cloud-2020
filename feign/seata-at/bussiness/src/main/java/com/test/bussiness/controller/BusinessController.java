package com.test.bussiness.controller;

import com.test.bussiness.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    /**
     * 购买下单，模拟全局事务提交
     */
    @RequestMapping(value = "/purchase/commit", produces = "application/json")
    public String purchaseCommit() {
        try {
            businessService.purchase("U100000", "C100000", 30);
        } catch (Exception exx) {
            return exx.getMessage();
        }
        return "全局事务提交";
    }

    /**
     * 购买下单，模拟全局事务回滚
     * 账户或库存不足
     */
    @RequestMapping("/purchase/rollback")
    public String purchaseRollback() {
        try {
            businessService.purchase("U100000", "C100000", 99999);
        } catch (Exception exx) {
            return exx.getMessage();
        }
        return "全局事务提交";
    }
}
