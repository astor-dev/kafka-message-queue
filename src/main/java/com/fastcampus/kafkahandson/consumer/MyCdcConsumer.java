package com.fastcampus.kafkahandson.consumer;

import com.fastcampus.kafkahandson.common.CustomObjectMapper;
import com.fastcampus.kafkahandson.model.MyCdcMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.fastcampus.kafkahandson.model.Topic.MY_CDC_TOPIC;

@Component
public class MyCdcConsumer {

    private final CustomObjectMapper objectMapper = new CustomObjectMapper();
    private volatile int retryCount = 0;

    @KafkaListener(
        topics = { MY_CDC_TOPIC },
        groupId = "cdc-consumer-group",
        concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> message, Acknowledgment acknowledgment) throws JsonProcessingException {
        String retryMessage = retryCount >0 ? "(retry: " + retryCount  + ")": "";
        MyCdcMessage myCdcMessage = objectMapper.readValue(message.value(), MyCdcMessage.class);
        System.out.println(retryMessage + "[Cdc Consumer] " + myCdcMessage.getOperationType() + " Message arrived (id: " + myCdcMessage.getId() + ") - " + myCdcMessage.getPayload() + " / time" + LocalDateTime.now());
        retryCount++;
        throw new RuntimeException("sth happened!");
//        acknowledgment.acknowledge();
    }
}
