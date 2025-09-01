package com.fastcampus.kafkahandson.api;

import com.fastcampus.kafkahandson.model.MyMessage;
import com.fastcampus.kafkahandson.producer.MyProducer;
import com.fastcampus.kafkahandson.producer.MySecondProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MyController {

    private final MyProducer myProducer;
    private final MySecondProducer mySecondProducer;

    @RequestMapping("/hello")
    String hello() {
        return "Hello World";
    }

    @PostMapping("/message")
    void message(
        @RequestBody MyMessage message
    ) {
        try {
            myProducer.sendMessage(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/second-message/{key}")
    void message2(
            @PathVariable("key") String key,
            @RequestBody String message
    ) {
        mySecondProducer.sendMessage(key, message);
    }
}
