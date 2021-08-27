package com.slowed.reddity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReddityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReddityApplication.class, args);
	}

}
