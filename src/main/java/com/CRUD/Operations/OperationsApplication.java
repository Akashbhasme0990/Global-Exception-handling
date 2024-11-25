package com.CRUD.Operations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


@SpringBootApplication
public class OperationsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(OperationsApplication.class, args);
		System.out.println(context.getEnvironment());
		ConfigurableEnvironment environment=context.getEnvironment();
	}

}
