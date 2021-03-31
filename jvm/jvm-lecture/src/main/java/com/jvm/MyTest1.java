package com.jvm;

public class MyTest1 {
    public static void main(String[] args) {
        Singleton.getSingleton();
        System.out.println("num1:" + Singleton.num1);
        System.out.println("num2:" + Singleton.num2);
    }
}

//准备阶段为变量赋予初始值
//初始化阶段，从上到下进行初始化
class Singleton {
    //加载 验证 准备 解析 初始化
    public static int num1 = 1;

    //准备阶段不会被屌用
    private Singleton() {
        System.out.println(num1);
        System.out.println(num2);
        num1++;
        num2++;
        System.out.println(num1);
        System.out.println(num2);
    }

    private static Singleton singleton = new Singleton();

    public static int num2 = 2;

    public static Singleton getSingleton() {
        return singleton;
    }
}