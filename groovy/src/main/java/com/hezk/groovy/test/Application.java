package com.hezk.groovy.test;

import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.Expando;

import java.io.File;
import java.io.IOException;

public class Application {

    private static final GroovyShell SHELL = new GroovyShell();

    public static void main(String[] args) throws IOException {

        Closure<?> closure1 = buildClosure("ddd_${user_id % 4}");
        Closure<?> result1 = closure1.rehydrate(new Expando(), null, null);
        result1.setResolveStrategy(Closure.DELEGATE_ONLY);
        result1.setProperty("user_id", 3);
        System.out.println(result1.call().toString());


        GroovyClassLoader classLoader = new GroovyClassLoader();
        String file = classLoader.getResource("groovy/t1.groovy").getFile();
        Class groovyClass = classLoader.parseClass(new File(file));
        try {
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            groovyObject.invokeMethod("myClosure", args);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Closure<?> buildClosure(String... strings) {
        String scriptText = "{->\"" + String.join("\n", strings) + "\"}";
        return (Closure<?>) SHELL.parse(scriptText).run();
    }
}
