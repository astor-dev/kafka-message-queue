package com.fastcampus.kafkahandson.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCdcMessage {
    private int id;
    private OperationType operationType;
    /**
     * C : before null -> after sth
     * U : before sth  -> after sth
     * D : before sth  -> after null
     */
    private Payload payload;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payload {
        private int id;
        private int userId;
        private int userAge;
        private String userName;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
