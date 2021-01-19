package com.hezk.agent.javassist;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyTransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;
        className = className.replace("/", ".");
        if ("StaticClass".equalsIgnoreCase(className)) {
            String methodName = "init";
            CtClass ctclass = null;
            try {
                ClassPool classPool = ClassPool.getDefault();
                ctclass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtClass.debugDump = "./dump";
                CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                ctmethod.addLocalVariable("startTime", CtClass.longType);
                ctmethod.insertBefore("startTime = System.currentTimeMillis();");
                ctmethod.insertAt(1, "try {\n" +
                        "com.hezk.agent.init.Init.init();\n" +
                        "java.lang.Thread.sleep(112L);\n" +
                        "System.out.println(12312);\n" +
                        "} catch (Exception e) {\n" +
                        "}\n");
                ctmethod.insertAfter("System.out.println(\"Execution Duration (ms): \"+ (System.currentTimeMillis() - startTime) );");
                byteCode = ctclass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return byteCode;
    }
}