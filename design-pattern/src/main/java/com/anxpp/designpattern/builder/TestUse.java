package com.anxpp.designpattern.builder;

public class TestUse {
    public static void main(String args[]) {
        IDateBuilder builder = new DateBuilder1().date(2066, 3, 5);
        System.out.println(builder.date());

        builder = new DateBuilder2().date(2066, 3, 5);
        System.out.println(builder.date());
    }
}