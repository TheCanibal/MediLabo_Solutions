package com.riskevaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RiskevaluatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiskevaluatorApplication.class, args);
    }

}
