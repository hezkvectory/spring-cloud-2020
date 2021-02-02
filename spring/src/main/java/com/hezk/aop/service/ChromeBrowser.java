package com.hezk.aop.service;

public class ChromeBrowser implements Browser {

    public void visitInternet() {
        System.out.println("visit YouTube");
        hello();
    }

    @Override
    public void hello() {
        System.out.println("ChromeBrowser.hello");
        hello1();
        hello2();
    }

    private void hello1() {
        System.out.println("ChromeBrowser.hello1");
    }

    protected void hello2() {
        System.out.println("ChromeBrowser.hello2");
    }

}