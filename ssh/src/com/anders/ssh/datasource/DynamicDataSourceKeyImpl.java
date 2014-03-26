package com.anders.ssh.datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.anders.ssh.datasource.loadbalance.LoadBalanceStrategy;
import com.anders.ssh.datasource.loadbalance.RandomLoadBalanceStrategy;

/**
 * 数据源key的存储控制器
 * 
 * @author 陆庆润 创建时间：2009-3-20
 */
public class DynamicDataSourceKeyImpl implements DynamicDataSourceKey, InitializingBean {
	private static final Logger log = Logger.getLogger(DynamicDataSourceKeyImpl.class);
	/**
	 * 数据源key的存储
	 */
	private static final ThreadLocal<String> DB_KEY = new ThreadLocal<String>();

	/**
	 * 读(从)库
	 */
	private Map<String, String> readDateSourceMap = new HashMap<String, String>();

	private Map<String, String> failedDataSourceMap = new ConcurrentHashMap<String, String>();

	/**
	 * 如果数据源标示位已经设置，是否进行替换操作， 默认为 false
	 */
	private boolean alwaysReplaceExist = false;

	private LoadBalanceStrategy<String> strategy;

	/**
	 * 写(主)库
	 */
	public String writeKey;

	public void setReadDateSourceMap(Map<String, String> readDateSourceMap) {
		this.readDateSourceMap = Collections.synchronizedMap(readDateSourceMap);

	}

	public String getKey(String key) {
		return readDateSourceMap.get(key);
	}

	public String getWriteKey() {
		return writeKey;
	}

	public void setWriteKey(String writeKey) {
		this.writeKey = writeKey;
	}

	public void setWriteKey() {
		DB_KEY.set(writeKey);
		log.debug("set data source writeKey[" + DB_KEY.get() + "]");

	}

	public void setReadKey() {
		if (!alwaysReplaceExist && DB_KEY.get() != null) {
			log.debug("data source already has a readKey[" + DB_KEY.get() + "] and ignore to replace it");
			return;
		}
		DB_KEY.set(strategy.elect());
		log.debug("set data source readKey[" + DB_KEY.get() + "]");
	}

	public void setKey(String key) {
		if (!alwaysReplaceExist && DB_KEY.get() != null && !writeKey.equals(key)) {
			log.debug("data source already has a readKey[" + DB_KEY.get() + "] and ignore to replace it");
			return;
		}

		DB_KEY.set(readDateSourceMap.get(key));
		log.debug("set data source key[" + DB_KEY.get() + "]");
	}

	public String getKey() {
		if (DB_KEY.get() == null) {
			setReadKey();
			// 老线索使用的是默认设置为写KEY
			// setWriteKey();
		}
		String key = DB_KEY.get();
		log.debug("get data source Key[" + DB_KEY.get() + "]");
		return key;
	}

	public void clearKey() {
		DB_KEY.remove();
	}

	public static void clearKeyForce() {
		DB_KEY.remove();
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

	public synchronized void removeDataSourceKey(String key) {
		if (readDateSourceMap.containsKey(key)) {
			failedDataSourceMap.put(key, readDateSourceMap.get(key));
			readDateSourceMap.remove(key);

			strategy.removeTarget(key);
		}
	}

	public synchronized void recoverDateSourceKey(String key) {
		if (failedDataSourceMap.containsKey(key)) {
			readDateSourceMap.put(key, readDateSourceMap.get(key));
			failedDataSourceMap.remove(key);

			strategy.recoverTarget(key);
		}
	}

	public void reSetKey() {
		String key = getKey();
		if (!writeKey.equals(key)) {
			setReadKey();
		}
	}

	public boolean isCurrentWriteKey() {
		String key = DB_KEY.get();
		return writeKey.equals(key);
	}

	public boolean hasReadKeyCandidate() {
		return !readDateSourceMap.isEmpty();
	}

	public synchronized boolean hasDataSourceFailed() {
		return !failedDataSourceMap.isEmpty();
	}

	public Map<String, String> getFailedDataSourceKeys() {
		return failedDataSourceMap;
	}

	public void afterPropertiesSet() throws Exception {
		// default using random strategy
		if (strategy == null) {
			List<String> list = new ArrayList<String>(readDateSourceMap.values());
			strategy = new RandomLoadBalanceStrategy(list);
		}
	}
}
