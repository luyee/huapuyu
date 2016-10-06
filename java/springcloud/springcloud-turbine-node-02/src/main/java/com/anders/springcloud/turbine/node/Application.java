package com.anders.springcloud.turbine.node;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableDiscoveryClient
@EnableCircuitBreaker
@RestController
public class Application {

	public static void main(String[] args) {
		// new SpringApplicationBuilder(Application.class).web(true).run(args);
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private HelloService helloService;

	@RequestMapping("/")
	public String hello() {
		return helloService.hello();
	}

	@Component
	public static class HelloService {
		
		private Random random = new Random();

		@HystrixCommand(fallbackMethod = "fallback")
		public String hello() {
			int randomInt = random.nextInt(10);
			if (randomInt % 2 == 0) {
				throw new RuntimeException();
			} else {
				return "Hello World 02";
			}
		}

		public String fallback() {
			return "Fallback 02";
		}
	}
}
