package com.hezk.agent.javassist;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class MyTransformer2 implements ClassFileTransformer {
    private List<String> classes = new ArrayList<>();

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        System.out.println(className);
        if ("com.vipkid.commons.springcloud.scheduler.internal.JobHandler$CallHandler".equalsIgnoreCase(className)) {
            String methodName = "handle";
            CtClass ctclass = null;
            try {
                ClassPool classPool = ClassPool.getDefault();
                classPool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
                ctclass = ClassPool.getDefault().get(className);
//                CtClass.debugDump = "./dump";

                CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                String newMethodName = methodName + "$old";
                ctmethod.setName(newMethodName);

                // 创建新的方法，复制原来的方法，名字为原来的名字
                CtMethod newMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);

                // 构建新的方法体
                StringBuilder bodyStr = new StringBuilder();
                bodyStr.append("{");
                bodyStr.append("long startTime = System.currentTimeMillis();\n");
                bodyStr.append("com.hezk.agent.init.MyHeaderUtils.handle($$);\n");

                CtClass returnType = ctmethod.getReturnType();
                if (returnType.equals(CtClass.voidType)) {
                    bodyStr.append(newMethodName + "($$);\n");// 调用原有代码，类似于method();($$)表示所有的参数
                } else {
                    bodyStr.append("Object result = " + newMethodName + "($$);\n");// 调用原有代码，类似于method();($$)表示所有的参数
                    bodyStr.append("return result;");
                }
                bodyStr.append("try {\n" +
                        "com.hezk.agent.init.Init.init();\n" +
                        "java.lang.Thread.sleep(112L);\n" +
                        "System.out.println(12312);\n" +
                        "} catch (Exception e) {\n" +
                        "}");
                bodyStr.append("long endTime = System.currentTimeMillis();");
                bodyStr.append("System.out.println(\"this class cost:\" +(endTime - startTime) +\"ms.\");");
                bodyStr.append("}");

                newMethod.setBody(bodyStr.toString());
                ctclass.addMethod(newMethod);
                return ctclass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}