package com.ws101.fformaran.EcommerceApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.ws101.fformaran"})
@EntityScan(basePackages = {"com.ws101.fformaran.model"})
@EnableJpaRepositories(basePackages = {"com.ws101.fformaran.repository"})
public class EcommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}

}
