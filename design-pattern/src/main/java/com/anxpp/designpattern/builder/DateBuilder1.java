package com.anxpp.designpattern.builder;

//具体生成器
public class DateBuilder1 implements IDateBuilder {
    private MyDate myDate = new MyDate();

    @Override
    public IDateBuilder date(int y, int m, int d) {
        myDate.date = y + "-" + m + "-" + d;
        return this;
    }

    @Override
    public MyDate date() {
        return myDate;
    }
}
