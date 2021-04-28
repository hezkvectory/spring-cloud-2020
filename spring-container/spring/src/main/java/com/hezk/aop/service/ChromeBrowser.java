package com.hezk.aop.service;

public class ChromeBrowser implements Browser {

    public void visitInternet() {
        System.out.println("visit YouTube");
    }

    @Override
    public void hello() {
        System.out.println("ChromeBrowser.hello");
    }

    private void hello1() {
        System.out.println("ChromeBrowser.hello1");
    }

    protected void hello2() {
        System.out.println("ChromeBrowser.hello2");
    }

}