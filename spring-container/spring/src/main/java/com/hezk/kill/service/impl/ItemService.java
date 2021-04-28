package com.hezk.kill.service.impl;

import com.hezk.kill.domain.ItemKill;
import com.hezk.kill.mapper.ItemKillMapper;
import com.hezk.kill.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements IItemService {

    @Autowired
    private ItemKillMapper itemKillMapper;

    @Override
    public List<ItemKill> getKillItems() throws Exception {
        return itemKillMapper.selectAll();
    }

    @Override
    public ItemKill getKillDetail(Integer id) throws Exception {
        ItemKill entity = itemKillMapper.selectById(id);
        if (entity == null) {
            throw new Exception("获取秒杀详情-待秒杀商品记录不存在");
        }
        return entity;
    }
}



















