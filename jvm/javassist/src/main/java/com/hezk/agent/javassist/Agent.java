package com.hezk.agent.javassist;

import java.lang.instrument.Instrumentation;

public class Agent {

    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("Agent.premain");
        inst.addTransformer(new MyTransformer2());
        inst.addTransformer(new MyTransformer3());
    }

}