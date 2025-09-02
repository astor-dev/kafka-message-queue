package com.fastcampus.kafkahandson.consumer;

import com.fastcampus.kafkahandson.model.MyMessage;
import com.fastcampus.kafkahandson.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class MyBatchConsumer {


    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @KafkaListener(
            topics = {Topic.MY_JSON_TOPIC},
            groupId = "batch-test-consumer-group",
            containerFactory = "batchKafkaListenerContainerFactory",
            concurrency = "1"
    )
    public void accept(List<ConsumerRecord<String, String>> messages, Acknowledgment acknowledgment) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("batch messages arrived! - ").append(messages.size()).append("\n");


        List<CompletableFuture<Void>> futures = messages.stream()
                .map(message -> CompletableFuture.runAsync(() -> {
                    try {
                        MyMessage myMessage = objectMapper.readValue(message.value(), MyMessage.class);

                        synchronized (stringBuilder) {
                            stringBuilder.append("ㄴ [Batch Consumer(").append(Thread.currentThread().getId()).append(")] Value ")
                                    .append(myMessage).append("/ Offset ").append(message.offset()).append("\n");
                        }
                    } catch (JsonProcessingException e) {
                        System.err.println("JSON 역직렬화 오류: " + e.getMessage());
                    }
                }, executorService))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println(stringBuilder);
        acknowledgment.acknowledge();
    }
}
