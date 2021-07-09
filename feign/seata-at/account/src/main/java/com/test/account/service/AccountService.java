package com.test.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean reduce(String userId, int money) {
        int ret = jdbcTemplate.update("update account_tbl set money = money - ? where user_id = ? AND money >= ?", new Object[] {money, userId, money});
        return ret > 0;
    }
}
