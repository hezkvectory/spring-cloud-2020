package com.hezk.standalone.runner;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.GenericMessageListener;
import org.springframework.stereotype.Component;

//@Component
//@KafkaListener(id = "group-0", topics = {"test"})
public class ConsumerKafkaHandler implements GenericMessageListener {

    @KafkaListener(id = "teste", topics = "test")
    public void bytesKey(ConsumerRecord<?, ?> consumerRecord) {
        System.out.println(consumerRecord.value());
    }

    @KafkaHandler
    public void onMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void onMessage(Object data) {
        System.out.println(data);
    }
    
}