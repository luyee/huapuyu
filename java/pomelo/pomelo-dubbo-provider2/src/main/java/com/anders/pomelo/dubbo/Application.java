package com.anders.pomelo.dubbo;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({ "classpath:dubbo-provider.xml" })
public class Application {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(Application.class);

	public static void main(String[] args) throws IOException, InterruptedException {
		// SpringApplication.run(Application.class, args);
		// com.alibaba.dubbo.container.Main.main(args);

		new SpringApplicationBuilder().sources(Application.class).web(false).run(args);

		System.in.read();
	}
}
