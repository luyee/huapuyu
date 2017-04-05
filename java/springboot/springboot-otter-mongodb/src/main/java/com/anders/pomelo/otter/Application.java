package com.anders.pomelo.otter;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.anders.pomelo.otter.cfg.MongoProps;
import com.anders.pomelo.otter.cfg.KafkaProps;

@EnableAutoConfiguration
@EnableConfigurationProperties({ KafkaProps.class, MongoProps.class })
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}
}
