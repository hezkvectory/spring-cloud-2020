package com.hezk.standalone.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        ParserConfig globalInstance = ParserConfig.getGlobalInstance();
//        globalInstance.putDeserializer(TestEnum.class, new EnumValueDeserializer());
        byte age = 20;
//        BeanTest beanTest = new BeanTest("1", false);
//        Student student = new Student("0001", "张三", age, true, beanTest);
//        String s = JSONObject.toJSONString(student);
//        System.out.println(s);
//
        for (int i = 0; i < 10; i++) {
            SerializeConfig parserConfig = new SerializeConfig();
            parserConfig.put(BeanTest.class, new BeanTestSerializer());
////            String jsonStr = "{\"id\":1,\"testEnum\":2}";
////            Bean bean = JSON.parseObject(jsonStr, Bean.class);
////            System.out.println(bean);
            BeanTest beanTest = new BeanTest("1", false);
            Student student = new Student("0001", "张三", age, true, beanTest);
            String s = JSONObject.toJSONString(student, parserConfig);
            JSON.toJSONString(student, parserConfig);
            System.out.println(s);
            if (i == 9) {
                System.in.read();
            }
//            System.out.println(JSONObject.toJSONString(beanTest));
        }
    }
}
