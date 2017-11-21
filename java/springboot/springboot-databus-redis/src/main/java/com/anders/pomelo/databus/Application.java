package com.anders.pomelo.databus;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.anders.pomelo.databus.cfg.RedisProps;
import com.moilioncircle.redis.replicator.RedisReplicator;
import com.moilioncircle.redis.replicator.Replicator;
import com.moilioncircle.redis.replicator.cmd.Command;
import com.moilioncircle.redis.replicator.cmd.CommandListener;
import com.moilioncircle.redis.replicator.cmd.impl.PingCommand;
import com.moilioncircle.redis.replicator.rdb.RdbListener;
import com.moilioncircle.redis.replicator.rdb.datatype.KeyValuePair;

@EnableAutoConfiguration
@EnableConfigurationProperties({ RedisProps.class })
@SpringBootApplication
public class Application implements CommandLineRunner {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
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
			public void preFullSync(Replicator replicator) {
            	LOGGER.debug("preFullSync");
			}

			@Override
			public void postFullSync(Replicator replicator, long checksum) {
				LOGGER.debug("postFullSync");
			}

			@Override
            public void handle(Replicator replicator, KeyValuePair<?> kv) {
            	LOGGER.debug(kv.getKey());
            }
        });
        replicator.addCommandListener(new CommandListener() {
            @Override
            public void handle(Replicator replicator, Command command) {
            	if (command instanceof PingCommand) {
            		
            	} else {
            		LOGGER.debug(command.toString());
            	}
            }
        });
        replicator.open();
        
        System.in.read();
        replicator.close();
	}
}
