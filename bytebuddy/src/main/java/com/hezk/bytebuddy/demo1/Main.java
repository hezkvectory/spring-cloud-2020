package com.hezk.bytebuddy.demo1;

import com.hezk.bytebuddy.demo1.sample.SayHelloDemo;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        SayHelloDemo say = new SayHelloDemo();
        say.sayHello("Tom");

        HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://www.baidu.com").openConnection();
        System.out.println("====1=" + urlConnection.getRequestMethod());
//        System.out.println("====2=" + urlConnection.getHeaderFields());
    }
}
