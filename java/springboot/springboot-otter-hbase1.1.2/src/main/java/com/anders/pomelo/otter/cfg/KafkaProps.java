package com.anders.pomelo.otter.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public class KafkaProps {

	private String brokers;
	private String groupId = "hbaseConsumer";
	private String topic;
	private String sessionTimeoutMs = "30000";
	private String enableAutoCommit = "true";
	private String autoCommitIntervalMs = "1000";
	private String maxPollRecords = "10";

	public String getBrokers() {
		return brokers;
	}

	public String getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setSessionTimeoutMs(String sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public String getEnableAutoCommit() {
		return enableAutoCommit;
	}

	public void setEnableAutoCommit(String enableAutoCommit) {
		this.enableAutoCommit = enableAutoCommit;
	}

	public String getAutoCommitIntervalMs() {
		return autoCommitIntervalMs;
	}

	public void setAutoCommitIntervalMs(String autoCommitIntervalMs) {
		this.autoCommitIntervalMs = autoCommitIntervalMs;
	}

	public String getMaxPollRecords() {
		return maxPollRecords;
	}

	public void setMaxPollRecords(String maxPollRecords) {
		this.maxPollRecords = maxPollRecords;
	}

	public void setBrokers(String brokers) {
		this.brokers = brokers;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
