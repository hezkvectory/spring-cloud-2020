package com.hezk.strategy;

public class StrategyContext {

    Double calPrice(Double price, RechargeTypeEnum type) {
        return StrategyFactory.getInstance().getStrategy(type.code).calPrice(price, type);
    }

}
