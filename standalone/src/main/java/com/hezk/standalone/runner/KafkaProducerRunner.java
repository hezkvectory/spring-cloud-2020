package com.hezk.standalone.runner;

import com.hezk.standalone.service.KafkaSender;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//@Component
public class KafkaProducerRunner implements ApplicationRunner {

    @Autowired
    private KafkaSender kafkaSender;

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "172.23.104.50:9092,172.23.104.50:9093,172.23.104.50:9094");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer<String, String>(kafkaProps);

        executor.submit(() -> {
            try {
                int i = 0;
                while (!Thread.currentThread().isInterrupted()) {
//                    kafkaSender.sendMsg("test1", "" + i++, "message-" + System.currentTimeMillis());
                    producer.send(new ProducerRecord("test1", "message-" + System.currentTimeMillis()));
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (Exception e) {
            }
        });
    }
}
