package com.nd.CompanyController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class CompanyControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyControllerApplication.class, args);
	}

}
