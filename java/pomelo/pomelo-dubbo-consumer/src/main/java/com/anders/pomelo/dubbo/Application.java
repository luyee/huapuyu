package com.anders.pomelo.dubbo;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import com.anders.pomelo.dubbo.service.UserService;

@SpringBootApplication
@ImportResource({ "classpath:dubbo-consumer.xml" })
public class Application {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(Application.class);

	public static void main(String[] args) throws IOException, InterruptedException {

		ApplicationContext ctx = new SpringApplicationBuilder().sources(Application.class).web(false).run(args);

		UserService userService = ctx.getBean(UserService.class);
		for (int i = 0; i < 100; i++) {
			System.out.println(userService.sayFuck("zhuzhen"));
		}

		System.in.read();
	}
}
