package com.hezk.feign.client.service.impl;

import com.hezk.feign.client.manager.IndexFeign;
import com.hezk.feign.client.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexFeign indexFeign;

    public String index() {
        return indexFeign.index();
    }
}
