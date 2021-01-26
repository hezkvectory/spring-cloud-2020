package com.hezk.bytebuddy.demo1.agent;

import com.hezk.bytebuddy.demo1.interceptor.*;
import com.hezk.bytebuddy.demo1.matcher.AbstractJunction;
import com.hezk.bytebuddy.demo1.matcher.ProtectiveShieldMatcher;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassInjector;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.Collections;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Agent {
    public static void premain(String arguments, Instrumentation inst) {
        System.out.println("Agent ClassLoader: " + Agent.class.getClassLoader());
        File temp = new File("./temp1");
        if (!temp.exists()) {
            temp.mkdirs();
        }
        ClassInjector.UsingInstrumentation.of(temp, ClassInjector.UsingInstrumentation.Target.BOOTSTRAP, inst)
                .inject(
                        Collections.singletonMap(
                                new TypeDescription.ForLoadedType(HttpURLConnectionMethodInterceptor.class),
                                ClassFileLocator.ForClassLoader.read(HttpURLConnectionMethodInterceptor.class).resolve()
                        )
                );

        AgentBuilder agentBuilder = new AgentBuilder.Default();
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                    TypeDescription typeDescription,
                                                    ClassLoader classLoader, JavaModule javaModule) {
                String className = typeDescription.getCanonicalName();
                System.out.println("++++++++ class name = " + className);
                try {

                    /**
                    builder = builder.visit(Advice.to(InstanceMethodSpendAdviceInterceptor.class).on(ElementMatchers.isMethod().and(ElementMatchers.not(ElementMatchers.<MethodDescription>isStatic()))));
                    builder = builder.visit(Advice.to(InstanceMethodSpendAdviceInterceptor2.class).on(ElementMatchers.isMethod().and(ElementMatchers.not(ElementMatchers.<MethodDescription>isStatic()))));

                    //拦截所有的，包括从父类继承过来的
                    builder = builder.method(ElementMatchers.named("print").and(ElementMatchers.takesArguments(1)))
                            .intercept(Advice.to(StaticMethodSpendAdviceInterceptor.class));

                     builder = builder.visit(Advice.to(MyAdvice.class).on(ElementMatchers.isMethod().and(ElementMatchers.<MethodDescription>nameStartsWith("method"))));


                     builder = builder.visit(Advice.to(MyAdvice1.class).on(ElementMatchers.<MethodDescription>named("methodB")))
                     .defineField("body", String.class, Visibility.PUBLIC, Ownership.STATIC, FieldManifestation.FINAL);//添加body属性

                    */

                    if (className.endsWith(".http.HttpURLConnection")) {
                        builder = builder.method(ElementMatchers.named("getRequestMethod").or(ElementMatchers.named("getHeaderFields")))
                                .intercept(Advice.to(HttpURLConnectionMethodAdviceInterceptor.class));

                        builder = builder.method(nameContains("getRequestMethod"))
                                .intercept(MethodDelegation.withDefaultConfiguration().to(HttpURLConnectionMethodInterceptor.class));

//                        builder = builder.method(ElementMatchers.named("getRequestMethod"))
//                                .intercept(MethodDelegation.to(HttpURLConnectionMethodInterceptor.class));

                    } else if (className.equalsIgnoreCase("com.hezk.bytebuddy.demo1.sample.SayHelloDemo")) {
                        builder = builder.method(ElementMatchers.named("sayHello"))//匹配任意方法
                                .intercept(MethodDelegation.withDefaultConfiguration().to(new SimplePackageInstanceMethodInterceptor()));

                        builder = builder.method(ElementMatchers.any())//匹配任意方法
                                .intercept(MethodDelegation.to(new SimplePackageStaticMethodInterceptor()));

                        builder = builder.constructor(ElementMatchers.takesArguments(0))//匹配没有参数的构造函数
                                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.to(new SimpleConstructedMethodInterceptor1())));

                        builder = builder.constructor(ElementMatchers.takesArguments(String.class))//匹配参数类型为String的构造函数
                                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.to(new SimpleConstructedMethodInterceptor2())));
                    }
//                    builder = builder.visit(Advice.to(HttpURLConnectionMethodAdviceInterceptor.class).on(ElementMatchers.named("getRequestMethod").or(ElementMatchers.named("getHeaderFields"))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return builder;
            }
        };

        //agentBuilder.enableBootstrapInjection(inst,temp);
        agentBuilder
                .ignore(nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("org.slf4j."))
                        .or(nameStartsWith("org.groovy."))
                        .or(nameContains("javassist"))
                        .or(nameContains(".asm."))
                        .or(nameContains(".reflectasm."))
                        .or(nameStartsWith("sun.reflect"))
                        .or(ElementMatchers.isSynthetic())
                )
                .type(buildMatch())
                .transform(transformer)
                .with(new MyListener())
                .enableBootstrapInjection(inst, temp)
                .installOn(inst);

//        agentBuilder
//                .ignore(ElementMatchers.none().and(ElementMatchers.nameStartsWith("com.hezk.bytebuddy.demo1.sample")))
////                .type(ElementMatchers.nameStartsWith("com.hezk.bytebuddy.demo1.sample").or(ElementMatchers.nameEndsWith(".http.HttpURLConnection")))
//                .type(ElementMatchers.nameEndsWith(".http.HttpURLConnection"))
////                .type(ElementMatchers.nameStartsWith("com.hezk.bytebuddy.demo1.sample"))
//                .transform(transformer)
//                .installOn(inst);

//        agentBuilder = agentBuilder
//                .ignore(ElementMatchers.none().and(ElementMatchers.nameStartsWith("com.hezk.bytebuddy.demo1.agent")))
//                .type(ElementMatchers.nameEndsWith(".http.HttpURLConnection"))
//                .transform(transformer);
    }

    public static ElementMatcher<? super TypeDescription> buildMatch() {
        ElementMatcher.Junction judge = new AbstractJunction<NamedElement>() {
            @Override
            public boolean matches(NamedElement target) {
                return target.getActualName().endsWith(".http.HttpURLConnection") || target.getActualName().equalsIgnoreCase("com.hezk.bytebuddy.demo1.sample.SayHelloDemo");
            }
        };
        judge = judge.and(not(isInterface())).and(not(isSetter()));
        return new ProtectiveShieldMatcher(judge);
    }

    private static class MyListener implements AgentBuilder.Listener {
        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

        }

        @Override
        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                                     boolean loaded, DynamicType dynamicType) {
            //修改后的类输出
            WeavingClassLog.INSTANCE.log(typeDescription, dynamicType);
        }

        @Override
        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                              boolean loaded) {
        }

        @Override
        public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded,
                            Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
        }
    }
}


