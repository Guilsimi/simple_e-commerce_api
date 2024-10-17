package com.example.ec_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class EcConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcConfigServerApplication.class, args);
	}

}
