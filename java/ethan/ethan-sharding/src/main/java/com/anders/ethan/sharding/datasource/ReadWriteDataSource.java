package com.anders.ethan.sharding.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.anders.ethan.sharding.common.ReadWriteKey;

public class ReadWriteDataSource extends AbstractRoutingDataSource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadWriteDataSource.class);

	private ReadWriteKey readWriteKey;

	@Override
	protected Object determineCurrentLookupKey() {
		String key = readWriteKey.getKey();
		LOGGER.debug("read write key is :　{}", key);
		return key;
	}

	public ReadWriteKey getReadWriteKey() {
		return readWriteKey;
	}

	public void setReadWriteKey(ReadWriteKey readWriteKey) {
		this.readWriteKey = readWriteKey;
	}
}
