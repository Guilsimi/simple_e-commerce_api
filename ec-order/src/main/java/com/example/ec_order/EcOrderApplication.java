package com.example.ec_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EcOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcOrderApplication.class, args);
	}

}
