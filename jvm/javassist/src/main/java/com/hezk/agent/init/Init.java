package com.hezk.agent.init;

/**
 * -javaagent:/Users/hezhengkui/Documents/coohua/ideaworkspace/github/spring-cloud-2020/jvm/javassist/target/agent.jar -agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n
 */
public class Init {
    public static void init() {
        System.out.println("Init.init");
    }
}
