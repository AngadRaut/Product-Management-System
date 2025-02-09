package com.pms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductManagementSystemApplication {
	private static final Logger log = LoggerFactory.getLogger(ProductManagementSystemApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementSystemApplication.class, args);
		log.info("\nApplication started!!");
	}
}