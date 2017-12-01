package com.anders.pomelo.otter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@EnableAutoConfiguration
@SpringBootApplication
@EnableApolloConfig({"application"})
public class Application implements CommandLineRunner {

	@Value("${pri_key1:defaultValue}")
	private String key;
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println(key);
	}
}
