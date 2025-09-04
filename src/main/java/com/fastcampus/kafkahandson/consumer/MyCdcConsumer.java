package com.fastcampus.kafkahandson.consumer;

import com.fastcampus.kafkahandson.model.MyCdcMessage;
import com.fastcampus.kafkahandson.model.MyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.fastcampus.kafkahandson.model.Topic.MY_CDC_TOPIC;
import static com.fastcampus.kafkahandson.model.Topic.MY_JSON_TOPIC;

@Component
public class MyCdcConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Integer> idHistoryMap = new ConcurrentHashMap<>(); // id에 대해 Exactly Once를 보장하기 위함

    @KafkaListener(
        topics = { MY_CDC_TOPIC },
        groupId = "cdc-consumer-group",
        concurrency = "1"
    )
    public void listen(ConsumerRecord<String, String> message, Acknowledgment acknowledgment) throws JsonProcessingException {
        MyCdcMessage myMessage = objectMapper.readValue(message.value(), MyCdcMessage.class);
        System.out.println("[Main Consumer(" + Thread.currentThread().getId() + ")] Message arrived! - " + myMessage.getPayload());
        acknowledgment.acknowledge();
    }

}
