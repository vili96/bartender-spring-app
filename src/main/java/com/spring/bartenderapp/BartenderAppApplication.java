package com.spring.bartenderapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
//@SpringBootApplication(scanBasePackages= {"com.spring.repositories","com.spring.models"})
@SpringBootApplication()
public class BartenderAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BartenderAppApplication.class, args);
	}

}
