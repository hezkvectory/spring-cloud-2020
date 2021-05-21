package com.hezk;

/**
 * 方法重载和方法重写
 * 静态分配和动态分配
 * 虚方法和非虚方法
 * invokevirtual和invokestatic、invokespecial
 * https://blog.csdn.net/zwx900102/article/details/108027295
 */
public class OverloadDemo {
    static class Human {
    }

    static class Man extends Human {
    }

    static class WoMan extends Human {
    }

    public void hello(Human human) {
        System.out.println("Hi,Human");
    }

    public void hello(Man man) {
        System.out.println("Hi,Man");
    }

    public void hello(WoMan woMan) {
        System.out.println("Hi,Women");
    }

    public static void main(String[] args) {
        OverloadDemo overloadDemo = new OverloadDemo();
        Human man = new Man();
        Human woman = new WoMan();

        overloadDemo.hello(man);
        overloadDemo.hello(woman);
    }
}