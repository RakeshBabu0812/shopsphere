package com.eproject.Eproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EprojectApplication.class, args);
	}

}
