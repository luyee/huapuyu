package com.anders.springcloud.zk.client;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RibbonClient("hello")
@EnableFeignClients
public class FeignApplication {

	@Autowired
	HelloClient client;

	@RequestMapping("/")
	public String hello() {
		return client.hello();
	}

	@FeignClient("zk.register")
	interface HelloClient {
		@RequestMapping(value = "/", method = GET)
		String hello();
	}

	public static void main(String[] args) {
		// new SpringApplicationBuilder(Application.class).web(true).run(args);

		SpringApplication.run(FeignApplication.class, args);
	}

}
