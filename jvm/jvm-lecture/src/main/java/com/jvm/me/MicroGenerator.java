package com.jvm.me;

import javassist.ClassPool;

/**
 * -XX:MetaspaceSize=32M -XX:MaxMetaspaceSize=32m -Xmx32m
 */
public class MicroGenerator {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100_000_000; i++) {
            generate("eu.plumbr.demo.Generated" + i);
        }
    }

    public static Class generate(String name) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        return pool.makeClass(name).toClass();
    }
}