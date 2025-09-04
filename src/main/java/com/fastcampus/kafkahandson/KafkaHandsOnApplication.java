package com.fastcampus.kafkahandson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.fastcampus.kafkahandson.data")
public class KafkaHandsOnApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaHandsOnApplication.class, args);
	}

}
