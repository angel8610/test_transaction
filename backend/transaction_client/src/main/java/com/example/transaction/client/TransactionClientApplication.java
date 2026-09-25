package com.example.transaction.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransactionClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionClientApplication.class, args);
	}

}
