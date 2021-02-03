package com.hezk.thrift;

import org.apache.thrift.TException;

import java.util.List;

public class CalculatorServiceImpl implements Calculator.Iface {
    @Override
    public int add(int num1, int num2) throws TException {
        System.out.println("num1:" + num1 + ",num2:" + num2);
        return num1 + num2;
    }

    @Override
    public int addList(List<Work> workList) throws TException {
        for (Work work : workList) {
            System.out.println(work);
        }
        return 1000;
    }
}
