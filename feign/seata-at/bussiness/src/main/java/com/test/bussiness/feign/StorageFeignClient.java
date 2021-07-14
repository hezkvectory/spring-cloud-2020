package com.test.bussiness.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "storage-service")
public interface StorageFeignClient {

    @GetMapping("/deduct")
    boolean deduct(@RequestParam("commodityCode") String commodityCode,
                @RequestParam("count") Integer count);

}
