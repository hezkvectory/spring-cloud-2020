package com.hezk.kill.service;

import com.hezk.kill.domain.ItemKill;

import java.util.List;

public interface IItemService {

    List<ItemKill> getKillItems() throws Exception;

    ItemKill getKillDetail(Integer id) throws Exception;
}
