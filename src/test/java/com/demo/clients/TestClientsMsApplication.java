package com.demo.clients;

import org.springframework.boot.SpringApplication;

public class TestClientsMsApplication {

	public static void main(String[] args) {
		SpringApplication.from(ClientsMsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
