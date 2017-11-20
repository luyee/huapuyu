package com.anders.pomelo.databus;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.anders.pomelo.databus.cfg.BinlogProps;
import com.anders.pomelo.databus.cfg.RedisProps;

@EnableAutoConfiguration
@EnableConfigurationProperties({ RedisProps.class })
@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	private RedisProps redisProps;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Replicator replicator = new RedisReplicator(redisProps.getAddress());
        replicator.addRdbListener(new RdbListener.Adaptor() {
            @Override
            public void handle(Replicator replicator, KeyValuePair<?> kv) {
                System.out.println(kv);
            }
        });
        replicator.addCommandListener(new CommandListener() {
            @Override
            public void handle(Replicator replicator, Command command) {
                System.out.println(command);
            }
        });
        replicator.open();
	}
}
