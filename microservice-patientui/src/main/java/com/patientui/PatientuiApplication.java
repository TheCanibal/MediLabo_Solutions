package com.patientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.patientui")
public class PatientuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientuiApplication.class, args);
	}

}
