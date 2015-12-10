package com.anders.ethan.sharding.common;

import com.anders.ethan.sharding.loadbalance.LoadBalance;

public class ReadWriteKey /* implements InitializingBean */{

	// private Map<String, String> readKey2DsName = new HashMap<String,
	// String>();
	// private Map<String, String> failedKey2DataSourceMap = new
	// ConcurrentHashMap<String, String>();
	private LoadBalance<String> loadBalance;
	private String writeKey;

	// public void setReadDateSources(Map<String, String> readDateSources) {
	// this.readKey2DsName = Collections.synchronizedMap(readDateSources);
	// }
	//
	// public String getReadDataSource(String key) {
	// return readKey2DsName.get(key);
	// }
	//
	// public Map<String, String> getReadDataSources() {
	// return readKey2DsName;
	// }

	public String getWriteKey() {
		return writeKey;
	}

	public void setWriteKey(String writeKey) {
		this.writeKey = writeKey;
	}

	public void setWriteKey() {
		ShardingUtil.setReadWriteKey(writeKey);
	}

	public void setReadKey() {
		// if (!alwaysReplace && ShardingUtil.getReadWriteKey() != null) {
		// return;
		// }
		if (loadBalance == null) {
			ShardingUtil.setReadWriteKey(writeKey);
		} else {
			ShardingUtil.setReadWriteKey(loadBalance.elect());
		}
	}

	// public void setKey(String key) {
	// if (!alwaysReplace && ShardingUtil.getReadWriteKey() != null
	// && !writeKey.equals(key)) {
	// if (!alwaysReplace && !writeKey.equals(key)) {
	// return;
	// }
	//
	// ShardingUtil.setReadWriteKey(readKey2DsName.get(key));
	// }

	public String getKey() {
		if (ShardingUtil.getReadWriteKey() == null) {
			return writeKey;
		}

		// if (ShardingUtil.getReadWriteKey() == null) {
		// setReadKey();
		// setWriteKey();
		// }
		return ShardingUtil.getReadWriteKey();
	}

	public LoadBalance<String> getLoadBalance() {
		return loadBalance;
	}

	public void setLoadBalance(LoadBalance<String> loadBalance) {
		this.loadBalance = loadBalance;
	}

	// public synchronized void removeDataSourceKey(String key) {
	// if (key2DateSourceMap.containsKey(key)) {
	// failedKey2DataSourceMap.put(key, key2DateSourceMap.get(key));
	// key2DateSourceMap.remove(key);
	// lb.removeTarget(key);
	// StrategyHolder.clearDataSourceKey();
	// }
	// }
	//
	// public synchronized void recoverDateSourceKey(String key) {
	// if (failedKey2DataSourceMap.containsKey(key)) {
	// key2DateSourceMap.put(key, failedKey2DataSourceMap.get(key));
	// failedKey2DataSourceMap.remove(key);
	// lb.recoverTarget(key);
	// }
	// }

	// public void resetKey() {
	// String key = getKey();
	// if (!writeKey.equals(key)) {
	// setReadKey();
	// }
	// }
	//
	// public boolean isCurrentWriteKey() {
	// String key = ShardingUtil.getReadWriteKey();
	// return writeKey.equals(key);
	// }

	// public boolean hasReadKey() {
	// return !key2DateSourceMap.isEmpty();
	// }
	//
	// public synchronized boolean hasFailedDataSource() {
	// return !failedKey2DataSourceMap.isEmpty();
	// }
	//
	// public Map<String, String> getFailedDataSourceKeys() {
	// return failedKey2DataSourceMap;
	// }

	// public void afterPropertiesSet() throws Exception {
	// if (loadBalance == null) {
	// List<String> list = new ArrayList<String>(readKey2DsName.values());
	// loadBalance = new RandomLoadBalance(list);
	// }
	// }

}
