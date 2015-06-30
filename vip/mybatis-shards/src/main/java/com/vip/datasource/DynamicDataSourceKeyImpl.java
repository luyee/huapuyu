package com.vip.datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.vip.datasource.strategy.LoadBalanceStrategy;
import com.vip.datasource.strategy.RandomLoadBalanceStrategy;

public class DynamicDataSourceKeyImpl implements DynamicDataSourceKey, InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceKeyImpl.class);

	private static final ThreadLocal<String> DS_KEY = new ThreadLocal<String>();
	private Map<String, String> readKey2DateSourceMap = new HashMap<String, String>();
	private Map<String, String> failedKey2DataSourceMap = new ConcurrentHashMap<String, String>();
	private boolean alwaysReplaceExist = false;
	private LoadBalanceStrategy<String> strategy;
	private String writeKey;

	@Override
	public void setReadDateSourceMap(Map<String, String> readDateSourceMap) {
		this.readKey2DateSourceMap = Collections.synchronizedMap(readDateSourceMap);

	}

	@Override
	public String getReadKey(String key) {
		return readKey2DateSourceMap.get(key);
	}

	@Override
	public String getWriteKey() {
		return writeKey;
	}

	@Override
	public void setWriteKey(String writeKey) {
		this.writeKey = writeKey;
	}

	@Override
	public void setWriteKey() {
		DS_KEY.set(writeKey);
		LOGGER.debug("set datasource write key [" + DS_KEY.get() + "]");

	}

	@Override
	public void setReadKey() {
		if (!alwaysReplaceExist && DS_KEY.get() != null) {
			LOGGER.debug("datasource already has a read key [" + DS_KEY.get() + "] and ignore to replace it");
			return;
		}
		DS_KEY.set(strategy.elect());
		LOGGER.debug("set data source readKey[" + DS_KEY.get() + "]");
	}

	@Override
	public void setKey(String key) {
		if (!alwaysReplaceExist && DS_KEY.get() != null && !writeKey.equals(key)) {
			LOGGER.debug("datasource already has a read key [" + DS_KEY.get() + "] and ignore to replace it");
			return;
		}

		DS_KEY.set(readKey2DateSourceMap.get(key));
		LOGGER.debug("set datasource key [" + DS_KEY.get() + "]");
	}

	@Override
	public String getKey() {
		if (DS_KEY.get() == null) {
			// default key is read key
			setReadKey(); // TODO Anders 这里是否要改为write
		}
		String key = DS_KEY.get();
		LOGGER.debug("get datasource Key [" + DS_KEY.get() + "]");
		return key;
	}

	@Override
	public void clearKey() {
		DS_KEY.remove();
	}

	public static void clearKeyForce() {
		DS_KEY.remove();
	}

	public boolean isAlwaysReplaceExist() {
		return alwaysReplaceExist;
	}

	public void setAlwaysReplaceExist(boolean alwaysReplaceExist) {
		this.alwaysReplaceExist = alwaysReplaceExist;
	}

	public LoadBalanceStrategy<String> getStrategy() {
		return strategy;
	}

	public void setStrategy(LoadBalanceStrategy<String> strategy) {
		this.strategy = strategy;
	}

	@Override
	public synchronized void removeDataSourceKey(String key) {
		if (readKey2DateSourceMap.containsKey(key)) {
			failedKey2DataSourceMap.put(key, readKey2DateSourceMap.get(key));
			readKey2DateSourceMap.remove(key);
			strategy.removeTarget(key);
		}
	}

	@Override
	public synchronized void recoverDateSourceKey(String key) {
		if (failedKey2DataSourceMap.containsKey(key)) {
			readKey2DateSourceMap.put(key, readKey2DateSourceMap.get(key));
			failedKey2DataSourceMap.remove(key);
			strategy.recoverTarget(key);
		}
	}

	@Override
	public void resetKey() {
		String key = getKey();
		if (!writeKey.equals(key)) {
			setReadKey();
		}
	}

	@Override
	public boolean isCurrentWriteKey() {
		String key = DS_KEY.get();
		return writeKey.equals(key);
	}

	@Override
	public boolean hasReadKey() {
		return !readKey2DateSourceMap.isEmpty();
	}

	@Override
	public synchronized boolean hasFailedDataSource() {
		return !failedKey2DataSourceMap.isEmpty();
	}

	@Override
	public Map<String, String> getFailedDataSourceKeys() {
		return failedKey2DataSourceMap;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (strategy == null) {
			List<String> list = new ArrayList<String>(readKey2DateSourceMap.values());
			// TODO Anders 写死了
			strategy = new RandomLoadBalanceStrategy(list);
		}
	}
}
