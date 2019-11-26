package com.shopping.vn;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shopping.vn.config.AppProperties;
import com.shopping.vn.utils.ServiceStatus;

@SpringBootApplication

public class ShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
		ServiceStatus[] serviceStatuses = ServiceStatus.values();
		for (ServiceStatus serviceStatus : serviceStatuses) {
			System.out.println(
					serviceStatus.getId() + " " + serviceStatus.getMessage() + " " + serviceStatus.getStatus());
		}
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean("AppProperties")
	public AppProperties getAppProperties() {
		return new AppProperties();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
