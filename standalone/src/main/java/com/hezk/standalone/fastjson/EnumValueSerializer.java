package com.hezk.standalone.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;

class EnumValueSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, //
                      Object object, //
                      Object fieldName, //
                      Type fieldType, //
                      int features) throws IOException {

        if (object instanceof EnumValue) {
            EnumValue baseEnum = (EnumValue) object;
            serializer.write(baseEnum.value());
        } else {
            serializer.write(object);
        }
    }
}