package com.hezk.standalone.fastjson;

public class BeanTest {
    String id;
//    TestEnum testEnum;
    Boolean studentSex;

    public BeanTest(String id, Boolean studentSex) {
        this.id = id;
//        this.testEnum = testEnum;
        this.studentSex = studentSex;
    }

    public Boolean getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(Boolean studentSex) {
        this.studentSex = studentSex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BeanTest{" +
                "id='" + id + '\'' +
                ", studentSex=" + studentSex +
                '}';
    }
}