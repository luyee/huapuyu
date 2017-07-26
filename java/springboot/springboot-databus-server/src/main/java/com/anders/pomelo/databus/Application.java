package com.anders.pomelo.databus;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.anders.pomelo.databus.cfg.BinlogProps;
import com.anders.pomelo.databus.cfg.KafkaProps;
import com.anders.pomelo.databus.cfg.ZkProps;

@EnableAutoConfiguration
@EnableConfigurationProperties({ KafkaProps.class, BinlogProps.class, ZkProps.class })
@SpringBootApplication
public class Application implements CommandLineRunner {

	private BinlogConsumer binlogConsumer;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}

	public Application(BinlogConsumer binlogConsumer) {
		this.binlogConsumer = binlogConsumer;
	}

	@Override
	public void run(String... args) throws Exception {
		binlogConsumer.start();
	}
}
