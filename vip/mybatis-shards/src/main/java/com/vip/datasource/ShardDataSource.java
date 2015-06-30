package com.vip.datasource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.vip.datasource.util.Constants;
import com.vip.mybatis.strategy.ShardStrategy;
import com.vip.mybatis.util.StrategyHolder;

public class ShardDataSource extends AbstractRoutingDataSource {

	private final Logger logger = LoggerFactory.getLogger(ShardDataSource.class);

	@Override
	protected Object determineCurrentLookupKey() {
		String key = StringUtils.EMPTY;
		try {
			ShardStrategy shardStrategy = StrategyHolder.getShardStrategy();
			if (shardStrategy == null)
				return Constants.DEFAULT_DYNAMIC_DS;

			key = shardStrategy.getTargetDynamicDataSource();
			if (StringUtils.isBlank(key))
				return Constants.DEFAULT_DYNAMIC_DS;

		}
		catch (Throwable e) {
			logger.error("get dynamic datasource key failed, will use default dynamic datasource");
		}
		return key;
	}

}
