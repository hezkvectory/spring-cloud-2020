package com.test.account.controller;

import com.test.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/reduce", produces = "application/json")
    public Boolean debit(String userId, int money) {
        return accountService.reduce(userId, money);
    }
}
