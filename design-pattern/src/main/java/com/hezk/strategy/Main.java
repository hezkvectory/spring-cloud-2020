package com.hezk.strategy;

/**
 * 策略模式，可以解决if else过多情况，同时符合oop设计原则，对扩展开发，修改关闭。减少修改业务逻辑影响范围
 * 同时结合单例设计模式，工厂设计模式
 */
public class Main {
    public static void main(String[] args) {

        StrategyContext context = new StrategyContext();

        context.calPrice(100d, RechargeTypeEnum.BANK);

        context.calPrice(100d, RechargeTypeEnum.ZHIFUBAO);

        context.calPrice(100d, RechargeTypeEnum.WEIXIN);
    }
}
