package com.interpackage.resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.interpackage.basedomains", "com.interpackage.resources"})
public class ResourcesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourcesApplication.class, args);
	}

}
