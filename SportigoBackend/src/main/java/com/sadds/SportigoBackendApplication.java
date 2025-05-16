package com.sadds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SportigoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportigoBackendApplication.class, args);
	}

}
