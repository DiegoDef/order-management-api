package br.com.diego.ordermanagement;

import org.springframework.boot.SpringApplication;

public class TestOrderManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrderManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
