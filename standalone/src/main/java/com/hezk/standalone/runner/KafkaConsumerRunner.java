package com.hezk.standalone.runner;

import com.hezk.standalone.service.KafkaSender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaConsumerRunner implements ApplicationRunner {

    @Autowired
    private KafkaSender kafkaSender;

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "172.23.104.50:9092,172.23.104.50:9093,172.23.104.50:9094");
        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("group.id", "ds");
        KafkaConsumer<String, String> consumer = new KafkaConsumer(kafkaProps);
        consumer.subscribe(Arrays.asList("test1"), new ConsumerRebalanceAdapter(consumer));

        executor.submit(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord record : records) {
                        System.out.println(record.topic() + ":" + record.partition() + ":" + record.offset() + ":" + record.key() + ":" + record.value());
                    }
                }
            } catch (Exception e) {
            }
        });
    }
}
