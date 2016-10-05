package com.anders.springcloud.hystrix.service;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class CallDependencyService {

	private Random random = new Random();

	@HystrixCommand(fallbackMethod = "fallback")
	public String mockGetUserInfo() {
		int randomInt = random.nextInt(10);
		if (randomInt < 8) {
			throw new RuntimeException("call dependency service fail.");
		} else {
			return "UserName:liaokailin;number:" + randomInt;
		}
	}

	public String fallback() {
		return "some exception occur call fallback method.";
	}
}
