package com.test.account.controller;

import com.test.account.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private TransferService transferService;

    @GetMapping("/transfer")
    public boolean doTransfer(String from, String to, int money) {
        //转账操作
        boolean ret = transferService.transfer(from, to, money);
        if (ret) {
            System.out.println("从账户" + from + "向" + to + "转账 " + money + "元 成功.");
            System.out.println();
        } else {
            System.out.println("从账户" + from + "向" + to + "转账 " + money + "元 失败.");
            System.out.println();
        }
        return ret;
    }

}
