package com.hezk.equal;

import org.junit.Test;

public class EqualsTest {

    @Test
    public void test1(){
        Integer a1 = 1;
        Integer a2 = 1;
        Integer a3 = 1243;
        Integer a4 = 1243;

        System.out.println(a1 == a2);
        System.out.println(a3 == a4);

        Long l1 = 1L;
        Long l2 = 1L;
        Long l3 = 1243L;
        Long l4 = 1243L;
        System.out.println(l1 == l2);
        System.out.println(l3 == l4);
    }
}
