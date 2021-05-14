package com.hezk.standalone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMsg(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    public void sendMsg(String topic, String key, String message) {
        kafkaTemplate.send(topic, message);
    }
}