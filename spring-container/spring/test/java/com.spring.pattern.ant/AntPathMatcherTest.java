package com.spring.pattern.ant;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class AntPathMatcherTest {

    @Test
    public void test() {
        PathMatcher pathMatcher = new AntPathMatcher();
        boolean match = pathMatcher.match("/test/{hezk}", "/test/123");
        System.out.println(match);
    }
}
