package com.test.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean deduct(String commodityCode, int count) {
        int ret = jdbcTemplate.update("update storage_tbl set count = count - ? where commodity_code = ? AND count >= ?",
            new Object[] {count, commodityCode, count});
        return ret > 0;
    }
}
