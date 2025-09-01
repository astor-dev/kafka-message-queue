package com.fastcampus.kafkahandson.consumer;

import com.fastcampus.kafkahandson.model.MyMessage;
import com.fastcampus.kafkahandson.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MyBatchConsumer {

    @KafkaListener(
            topics = {Topic.MY_JSON_TOPIC},
            groupId = "batch-test-consumer-group",
            containerFactory = "batchKafkaListenerContainerFactory"
    )
    public void accept(List<ConsumerRecord<String, String>> messages) {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("batch messages arrived! - " + messages.size());
        for (ConsumerRecord<String, String> message : messages) {
            MyMessage myMessage;
            try {
                myMessage = objectMapper.readValue(message.value(), MyMessage.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ㄴ [Batch Consumer] Value " + myMessage + " / Offset " + message.offset());
        }
    }
}
