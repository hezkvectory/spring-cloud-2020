package com.hezk.standalone.fastjson;

enum TestEnum implements EnumValue {
    TEST1(1),
    TEST2(2);
    private int value;

    TestEnum(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}