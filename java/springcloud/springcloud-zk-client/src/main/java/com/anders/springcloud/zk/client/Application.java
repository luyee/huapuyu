package com.anders.springcloud.zk.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class Application {

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	private DiscoveryClient discovery;

	@RequestMapping("/discovery")
	public Object discovery() {
		return loadBalancer.choose("zk.register");
	}

	@RequestMapping("/all")
	public Object all() {
		return discovery.getServices();
	}

	public static void main(String[] args) {
		// new SpringApplicationBuilder(Application.class).web(true).run(args);

		SpringApplication.run(Application.class, args);
	}

	//  以下为ribbon代码

	@Autowired
	RestTemplate client;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate template = new RestTemplate();
		SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) template.getRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(3000);
		return template;
	}

	@RequestMapping("/hello")
	public String helloWorld() {
		return client.getForObject("http://zk.register/", String.class);
	}
}
