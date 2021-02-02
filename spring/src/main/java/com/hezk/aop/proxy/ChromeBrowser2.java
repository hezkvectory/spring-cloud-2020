package com.hezk.aop.proxy;

import org.springframework.stereotype.Component;

@Component
public class ChromeBrowser2 {

    public void visitInternet() {
        System.out.println("visit YouTube");
    }

    private void hello1() {
        System.out.println("ChromeBrowser.hello1");
    }

    protected void hello2() {
        System.out.println("ChromeBrowser.hello2");
    }

}