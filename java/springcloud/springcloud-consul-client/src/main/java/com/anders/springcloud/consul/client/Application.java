package com.anders.springcloud.consul.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		// zk和eureka使用的是zk.register和eureka.register，但consul不能用.进行分割，所有使用-进行分割
		return loadBalancer.choose("consul-register");
	}

	@RequestMapping("/all")
	public Object all() {
		return discovery.getServices();
	}

	public static void main(String[] args) {
		// new SpringApplicationBuilder(Application.class).web(true).run(args);

		SpringApplication.run(Application.class, args);
	}
}
