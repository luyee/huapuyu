package com.anders.experiment.jmockit;

import java.util.Map;

public interface DynamicDataSourceKey {
	public abstract void setReadDateSourceMap(Map<String, String> readDateSourceMap);

	public abstract String getKey(String key);

	public abstract String getWriteKey();

	public abstract void setWriteKey(String writeKey);

	public abstract void setWriteKey();

	public abstract void setReadKey();

	public abstract void setKey(String key);

	public abstract String getKey();

	public abstract void clearKey();

	public void removeDataSourceKey(String key);

	public void recoverDateSourceKey(String key);

	public void reSetKey();

	public boolean isCurrentWriteKey();

	public boolean hasReadKeyCandidate();

	public boolean hasDataSourceFailed();

	public Map<String, String> getFailedDataSourceKeys();

}