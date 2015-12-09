package com.anders.ethan.sharding.common;

public class ShardingHolder {
	// private String repositoryShardingKey;
	private String readWriteKey;

	// private boolean transaction = false;

	// public void setRepositoryShardingKey(String repositoryShardingKey) {
	// this.repositoryShardingKey = repositoryShardingKey;
	// }
	//
	// public String getRepositoryShardingKey() {
	// return this.repositoryShardingKey;
	// }

	// public boolean getTransaction() {
	// return this.transaction;
	// }

	public String getReadWriteKey() {
		return readWriteKey;
	}

	public void setReadWriteKey(String readWriteKey) {
		this.readWriteKey = readWriteKey;
	}

	// public void setTransaction(boolean transaction) {
	// this.transaction = transaction;
	// }
}
