package com.hezk.strategy;

public enum RechargeTypeEnum {
    BANK(1, "网上银行"),
    ZHIFUBAO(2, "支付宝"),
    WEIXIN(3, "微信");

    int code;
    String description;

    RechargeTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public RechargeTypeEnum valueOf(int code) {
        for (RechargeTypeEnum e : values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
