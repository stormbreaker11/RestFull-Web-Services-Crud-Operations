package com.example.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication

public class CustomExceptionResponseEntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomExceptionResponseEntityApplication.class, args);
	}

}
