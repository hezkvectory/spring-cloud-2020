package com.test.account.service;

/**
 * 转账服务
 */
public interface TransferService {

    /**
     * 转账操作
     *
     * @param from   扣钱账户
     * @param to     加钱账户
     * @param amount 转账金额
     * @return
     */
    boolean transfer(String from, String to, int amount);


}
