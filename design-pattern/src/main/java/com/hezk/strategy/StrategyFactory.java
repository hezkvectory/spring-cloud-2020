package com.hezk.strategy;

import java.util.HashMap;
import java.util.Map;

public class StrategyFactory {

    private static final StrategyFactory instance = new StrategyFactory();

    private StrategyFactory() {
    }

    private static final Map<Integer, Strategy> container = new HashMap<>();

    static {
        container.put(RechargeTypeEnum.BANK.code, new BankStrategy());
        container.put(RechargeTypeEnum.ZHIFUBAO.code, new ZhifubaoStrategy());
        container.put(RechargeTypeEnum.WEIXIN.code, new WeixinStrategy());
    }

    public static StrategyFactory getInstance() {
        return instance;
    }

    public Strategy getStrategy(int code) {
        return container.get(code);
    }

}
