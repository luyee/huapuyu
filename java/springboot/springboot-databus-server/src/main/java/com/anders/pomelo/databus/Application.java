package com.anders.pomelo.databus;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.anders.pomelo.databus.cfg.KafkaProps;
import com.anders.pomelo.databus.cfg.MySQLProps;
import com.anders.pomelo.databus.cfg.ZkProps;

@EnableAutoConfiguration
@EnableConfigurationProperties({ KafkaProps.class, MySQLProps.class, ZkProps.class })
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}
}
