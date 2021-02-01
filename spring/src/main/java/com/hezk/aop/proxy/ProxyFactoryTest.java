package com.hezk.aop.proxy;

import com.hezk.aop.advice.BrowserAfterReturningAdvice;
import com.hezk.aop.advice.BrowserAroundAdvice;
import com.hezk.aop.advice.BrowserBeforeAdvice;
import com.hezk.aop.service.Browser;
import com.hezk.aop.service.ChromeBrowser;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;

public class ProxyFactoryTest {

    public static void main(String[] args) {
        // 1.创建代理工厂
        ProxyFactory factory = new ProxyFactory();
        // 2.设置目标对象
        factory.setTarget(new ChromeBrowser());
        // 3.设置代理实现接口
        factory.setInterfaces(new Class[]{Browser.class});
        // 4.添加前置增强
        factory.addAdvice(new BrowserBeforeAdvice());
        // 5.添加后置增强
        factory.addAdvice(new BrowserAfterReturningAdvice());

        // 创建正则表达式切面类
        RegexpMethodPointcutAdvisor advisor = new RegexpMethodPointcutAdvisor();
        // 添加环绕增强
        advisor.setAdvice(new BrowserAroundAdvice());
        // 设置切入点正则表达式
        advisor.setPattern("com.hezk.aop.service.ChromeBrowser.visitInternet");
        factory.addAdvisor(advisor);
        factory.setProxyTargetClass(true);

        // 6.获取代理对象
        Browser browser = (Browser) factory.getProxy();

        browser.visitInternet();
    }
}
