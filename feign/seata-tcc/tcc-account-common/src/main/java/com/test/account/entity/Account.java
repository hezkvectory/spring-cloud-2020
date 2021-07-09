package com.test.account.entity;

/**
 * 账户
 */
public class Account {

    /**
     * 账户
     */
    private String userId;
    /**
     * 余额
     */
    private int amount;
    /**
     * 冻结金额
     */
    private int freezedAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFreezedAmount() {
        return freezedAmount;
    }

    public void setFreezedAmount(int freezedAmount) {
        this.freezedAmount = freezedAmount;
    }


}
