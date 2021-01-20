package com.hezk.agent.init;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import okhttp3.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

public class MyHeaderUtils {
    private static TransmittableThreadLocal<String> l5dHeaders = new TransmittableThreadLocal<>();

    public static String getL5dHeaders() {
        String header = l5dHeaders.get();
        return header == null ? "" : header;
    }

    public static void interceptHeaders(Object... args) {
        System.out.println("==== Intercept headers ====:" + args);
    }

    public static void interceptHeaders(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("==== Before Intercept headers ====:" + request);
        System.out.println("MyHeaderUtils:" + MyHeaderUtils.class.getClassLoader());
        String header = request.getHeader("l5d-ctx-dtab");
        System.out.println("Intercept header l5d-ctx-dtab: " + header);
        if (header != null) {
            l5dHeaders.set(header);
        }
    }

    public static void afterInterceptHeaders(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("==== After Intercept headers ====:" + request);
        System.out.println("Clear header l5d-ctx-dtab: ");
        l5dHeaders.remove();
    }

    public static void handle(HttpExchange exchange) {
        Headers headers = exchange.getRequestHeaders();
        Iterator<String> keys = headers.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = headers.getFirst(key);
            if ("l5d-ctx-dtab".equalsIgnoreCase(key)) {
                l5dHeaders.set(value);
            }
        }
    }

}
