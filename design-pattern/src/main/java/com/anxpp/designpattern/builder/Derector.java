package com.anxpp.designpattern.builder;

//指挥者
public class Derector {
    private IDateBuilder builder;

    public Derector(IDateBuilder builder) {
        this.builder = builder;
    }

    public MyDate getDate(int y, int m, int d) {
        builder.date(y, m, d);
        return builder.date();
    }
}
