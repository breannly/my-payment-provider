package com.example.mytransactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestMyTransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MyTransactionServiceApplication::main).with(TestMyTransactionServiceApplication.class).run(args);
	}

}
