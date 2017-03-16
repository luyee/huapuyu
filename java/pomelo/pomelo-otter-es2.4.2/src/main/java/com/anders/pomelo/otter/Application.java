package com.anders.pomelo.otter;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.anders.pomelo.otter.cfg.EsProps;
import com.anders.pomelo.otter.cfg.KafkaProps;

@EnableAutoConfiguration
@EnableConfigurationProperties({ KafkaProps.class, EsProps.class })
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		// for (int i = 1; i < 100; i++) {
		// System.out.println(String.format("insert into anders1.tb_order1 (name) value ('zhuzhen%d');", i));
		// System.out.println(String.format("insert into anders.tb_order (name) value ('zhuzhen%d');", i));
		// System.out.println(String.format("insert into anders1.tb_bank1 (name) value ('zhuzhen%d');", i));
		// System.out.println(String.format("insert into anders.tb_bank (name) value ('zhuzhen%d');", i));
		//
		// }

		SpringApplication.run(Application.class, args);
	}
}
