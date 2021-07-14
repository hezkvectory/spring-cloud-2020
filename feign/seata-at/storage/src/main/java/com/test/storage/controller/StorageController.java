package com.test.storage.controller;

import com.test.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping(value = "/deduct", produces = "application/json")
    public Boolean deduct(String commodityCode, Integer count) {
        return storageService.deduct(commodityCode, count);
    }
}
