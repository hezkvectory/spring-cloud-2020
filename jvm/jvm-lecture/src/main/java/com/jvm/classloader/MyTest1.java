package com.jvm.classloader;

/**
 * 对于静态字段、静态方法来说，只有直接定义了该字段、方法的类才会被初始化
 * 当一个类在初始化时，要求其父类全部都已经初始化完毕了
 *
 * XX:+TraceClassLoading 参数加入后，再重新运行MyTest1，类加载的结果如下。
 [Loaded zy.jvm.classloader.MyTest1 from file:/D:/gitRepo/jvm_lecture/build/classes/java/main/]
 [Loaded zy.jvm.classloader.MyParent1 from file:/D:/gitRepo/jvm_lecture/build/classes/java/main/]
 [Loaded zy.jvm.classloader.MyChild1 from file:/D:/gitRepo/jvm_lecture/build/classes/java/main/]
 * 说明自定义类的加载顺序为MyTest1 > MyParent1 > MyChild1
 * @author
 */

public class MyTest1 {
    public static void main(String[] args) {
        System.out.println(MyChild1.str);
        // 运行结果
//        MyParent1 static block
//        hello world
//        System.out.println(MyChild1.str2);
        // 运行结果
//        MyParent1 static block
//        MyChild1 static block
//        welcome
//        MyChild1.myPrentMethod1();
        // 运行结果
//        MyParent1 static block
//        MyParent1 static myPrentMethod1

    }
}

class MyParent1 {
    public static String str = "hello world";

    public static void myPrentMethod1(){
        System.out.println("MyParent1 static myPrentMethod1");
    }

    static {
        System.out.println("MyParent1 static block");
    }
}

class MyChild1 extends MyParent1 {

    public static String str2 = "welcome";

    static {
        System.out.println("MyChild1 static block");
    }
}