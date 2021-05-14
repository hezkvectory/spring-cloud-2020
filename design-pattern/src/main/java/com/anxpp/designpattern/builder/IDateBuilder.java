package com.anxpp.designpattern.builder;

//抽象生成器
public interface IDateBuilder {
    IDateBuilder date(int y, int m, int d);

    MyDate date();
}
