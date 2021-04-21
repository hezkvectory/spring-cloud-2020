package com.hezk.standalone.fastjson;

public class Student {
    String studentId;
    String studentName;
    Byte studentAge;
    Boolean studentSex;
    BeanTest beanTest;

    public Student(String studentId, String studentName, Byte studentAge, Boolean studentSex, BeanTest beanTest) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentAge = studentAge;
        this.studentSex = studentSex;
        this.beanTest = beanTest;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Byte getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(Byte studentAge) {
        this.studentAge = studentAge;
    }

    public Boolean getStudentSex() {
        return studentSex;
    }

    public BeanTest getBeanTest() {
        return beanTest;
    }

    public void setBeanTest(BeanTest beanTest) {
        this.beanTest = beanTest;
    }

    public void setStudentSex(Boolean studentSex) {
        this.studentSex = studentSex;
    }
}
