package com.vipkid.feign.client.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "feign-server")
public interface IndexFeign {

    @RequestMapping("/index")
    String index();
}
