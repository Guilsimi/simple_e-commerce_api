package com.example.ec_product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EcProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcProductApplication.class, args);
	}

}
