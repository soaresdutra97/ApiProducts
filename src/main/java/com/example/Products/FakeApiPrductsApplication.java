package com.example.Products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FakeApiPrductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakeApiPrductsApplication.class, args);
	}

}
