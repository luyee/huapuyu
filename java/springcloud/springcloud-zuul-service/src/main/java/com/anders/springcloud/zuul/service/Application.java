package com.anders.springcloud.zuul.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableAutoConfiguration
// @Configuration
public class Application {

	@RequestMapping(value = "/available")
	public String available() {
		return "available";
	}

	@RequestMapping(value = "/checkedout")
	public String checkedOut() {
		return "checked-out";
	}

	public static void main(String[] args) {
		// new SpringApplicationBuilder(Application.class).web(true).run(args);
		SpringApplication.run(Application.class, args);
	}
}
