package com.anders.pomelo.dubbo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:dubbo-provider.xml"})
public class Application {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(Application.class);
	
//	@Bean
//    public CountDownLatch closeLatch() {
//        return new CountDownLatch(1);
//    }

	public static void main(String[] args) throws IOException, InterruptedException {
//		SpringApplication.run(Application.class, args);
//		com.alibaba.dubbo.container.Main.main(args);
		
		ApplicationContext ctx = new SpringApplicationBuilder()
                .sources(Application.class)
                .web(false)
                .run(args);

//        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
//        closeLatch.await();
		
		System.in.read();
	}
}
