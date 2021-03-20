package com.hezk.standalone.runner;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConsumerRebalanceAdapter implements ConsumerRebalanceListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerRebalanceAdapter.class);

    private KafkaConsumer<String, String> kafkaConsumer;

    public ConsumerRebalanceAdapter(KafkaConsumer<String, String> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        System.out.println("ConsumerRebalanceAdapter.onPartitionsRevoked");
        kafkaConsumer.commitSync();
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.println(new Gson().toJson(partitions));
        List<TopicPartition> latestPartition = new ArrayList<>();
        for (TopicPartition partition : partitions) {
            OffsetAndMetadata offset = kafkaConsumer.committed(partition);
            if (offset == null) {
                System.out.printf("Topic：%s partition: %s 从最新消息处开始消费\n", partition.topic(), partition.partition());
                latestPartition.add(partition);
            } else {
                System.out.printf("Topic：%s partition: %s 从offset： %s 处开始消费\n", partition.topic(), partition.partition(), offset.offset());
                kafkaConsumer.seek(partition, offset.offset() + 1);
            }
        }
        if (!latestPartition.isEmpty()) {
            kafkaConsumer.seekToEnd(latestPartition);
        }
    }

}
