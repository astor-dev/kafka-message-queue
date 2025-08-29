package com.fastcampus.kafkahandson.consumer;

import com.fastcampus.kafkahandson.model.MyMessage;
import com.fastcampus.kafkahandson.model.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class MySecondConsumer {

    @KafkaListener(
            topics = {Topic.MY_SECOND_TOPIC},
            groupId = "test-consumer-group",
            containerFactory = "secondKafkaListenerContainerFactory"
    )
    public void accept(ConsumerRecord<String, String> message) {
        System.out.println("second message arrived! - " + message.value());
    }
}
