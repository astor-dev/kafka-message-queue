package com.fastcampus.kafkahandson.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MyModel {
    private Integer id;
    private Integer userId;
    private Integer userAge;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MyModel create(
            Integer userId,
            Integer userAge,
            String userName,
            String content
    ) {
        return new MyModel(
                null,
                userId,
                userAge,
                userName,
                content,
                null,
                null
        );
    }


}
