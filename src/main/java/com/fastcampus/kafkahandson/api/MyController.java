package com.fastcampus.kafkahandson.api;

import com.fastcampus.kafkahandson.model.MyMessage;
import com.fastcampus.kafkahandson.model.MyModel;
import com.fastcampus.kafkahandson.producer.MyProducer;
import com.fastcampus.kafkahandson.service.MyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyController {

    private final MyService myService;

    @GetMapping("/greetings")
    List<MyModel> getAll() {
        return myService.findAll();
    }

    @GetMapping("/greetings/{id}")
    MyModel get(@PathVariable Integer id ) {
        return myService.findById(id);
    }

    @PutMapping("/greeting/{id}")
    ResponseEntity<MyModel> update(@RequestBody @Valid Request request, @PathVariable Integer id) {
        MyModel myModel = myService.findById(id);
        if(myModel == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        myModel.setContent(request.getContent());
        myModel.setUserName(request.getUserName());
        myModel.setUserAge(request.getUserAge());

        return ResponseEntity.ok(myService.save(myModel));
    }


    @PostMapping("/greeting")
    MyModel create(@RequestBody @Valid Request request) {
        MyModel myModel = MyModel.create(
                request.userId,
                request.userAge,
                request.userName,
                request.content
        );
        return myService.save(myModel);
    }


    @Data
    public static class Request {
        @NotNull
        Integer userId;
        @NotNull
        Integer userAge;
        @NotNull
        String userName;
        @NotNull
        String content;

    }
}
