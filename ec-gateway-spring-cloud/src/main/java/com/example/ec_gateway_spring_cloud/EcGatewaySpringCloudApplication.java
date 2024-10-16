package com.example.ec_gateway_spring_cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EcGatewaySpringCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcGatewaySpringCloudApplication.class, args);
	}

}
