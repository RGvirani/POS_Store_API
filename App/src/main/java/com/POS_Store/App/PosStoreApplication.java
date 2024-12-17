package com.POS_Store.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class PosStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosStoreApplication.class, args);
	}

}
