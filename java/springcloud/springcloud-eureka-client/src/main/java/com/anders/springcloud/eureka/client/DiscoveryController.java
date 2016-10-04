package com.anders.springcloud.eureka.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@EnableDiscoveryClient
@RestController
public class DiscoveryController {
	
	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/")
	public String home() {
		return "Hello World";
	}
	
	@RequestMapping("/discovery")
	public Object discovery() {
		return loadBalancer.choose("eureka.register");
	}

	@RequestMapping("/disc")
	public String doDiscoveryService() {
		StringBuilder buf = new StringBuilder();
		List<String> serviceIds = discoveryClient.getServices();
		if (!CollectionUtils.isEmpty(serviceIds)) {
			for (String s : serviceIds) {
				System.out.println("serviceId:" + s);
				List<ServiceInstance> serviceInstances = discoveryClient.getInstances(s);
				if (!CollectionUtils.isEmpty(serviceInstances)) {
					for (ServiceInstance si : serviceInstances) {
						buf.append("[" + si.getServiceId() + " host=" + si.getHost() + " port=" + si.getPort() + " uri=" + si.getUri() + "]");
					}
				} else {
					buf.append("no service.");
				}
			}
		}
		
		System.out.println(discoveryClient.getLocalServiceInstance().getServiceId());

		return buf.toString();
	}

}
