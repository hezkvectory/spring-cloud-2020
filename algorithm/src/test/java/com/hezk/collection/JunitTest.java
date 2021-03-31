package com.hezk.collection;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by hezhengkui on 07/12/2018.
 */
public class JunitTest {

    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long addressOf(Object o) throws Exception {

        Object[] array = new Object[] { o };

        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }
        return (objectAddress);
    }

    @org.junit.Test
    public void test1() throws Exception {

        Integer i1 = 1011;
        Integer i2 = 1111;
        System.out.println(addressOf(i1) + "," + addressOf(i2));
        swap(i1, i2);
        System.out.println(addressOf(i1) + "," + addressOf(i2));
    }

    private void swap(Integer i1, Integer i2) throws Exception {
        System.out.println(addressOf(i1) + "--," + addressOf(i2));
        Integer tmp = i1;
        i1 = i2;
        i2 = tmp;
        System.out.println(addressOf(i1) + "--," + addressOf(i2));
    }

    @Test
    public void test() throws Exception {
        Person person1 = new Person("zhangsan", 1);
        Person person2 = new Person("lisi", 2);
        address(person1, person2);
        printp(person1, person2);
        swap(person1, person2);
        address(person1, person2);
//        printp(person1, person2);
    }

    private void printp(Person person1, Person person2) {
//        System.out.println(person1);
//        System.out.println(person2);
    }


    private void address(Person person1, Person person2) throws Exception {
        System.out.println(addressOf(person1));
        System.out.println(addressOf(person2));
    }

    private void swap(Person person1, Person person2) throws Exception {
//        address(person1, person2);
        Person tmpPerson = person1;
        System.out.println(addressOf(tmpPerson));
        person1 = person2;
        person2 = tmpPerson;

        person2.setId(32);
//        address(person1, person2);
    }
}

class Person {
    String name;
    Integer id;

    public Person(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}