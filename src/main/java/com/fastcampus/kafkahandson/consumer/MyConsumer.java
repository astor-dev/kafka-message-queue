package com.fastcampus.kafkahandson.consumer;

import com.fastcampus.kafkahandson.model.MyMessage;
import com.fastcampus.kafkahandson.model.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Component
public class MyConsumer {

    @KafkaListener(
            topics = {Topic.MY_JSON_TOPIC},
            groupId = "test-consumer-group"
    )
    public void accept(ConsumerRecord<String, MyMessage> message, Acknowledgment acknowledgment) {
        System.out.println("Message arrived! - " + message.value());
        acknowledgment.acknowledge();
    }
}
