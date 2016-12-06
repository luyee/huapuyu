package com.anders.pomelo.otter.poll;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArraySet;

import kafka.utils.ShutdownableThread;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.otter.node.etl.load.loader.db.Message;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import com.anders.pomelo.otter.cfg.KafkaProps;

public class ConsumerThread extends ShutdownableThread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerThread.class);

	private final KafkaConsumer<String, byte[]> consumer;
	private final TransportClient client;
	// private final Client client;
	private final MessagePack messagePack;
	// TODO Anders 下面代码需要删除，注意oom
	private final CopyOnWriteArraySet<String> pkCache = new CopyOnWriteArraySet<String>();

	public ConsumerThread(KafkaProps kafkaProperties, TransportClient client, MessagePack messagePack) {
		super("otterConsumer", false);

		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaProperties.getBrokers());
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		// kafkaProps.put("fetch.min.bytes", "1");
		props.put("group.id", kafkaProperties.getGroupId());
		// kafkaProps.put("heartbeat.interval.ms", "3000");
		// kafkaProps.put("max.partition.fetch.bytes", "");
		props.put("session.timeout.ms", kafkaProperties.getSessionTimeoutMs());
		// kafkaProps.put("auto.offset.reset", "");
		// kafkaProps.put("connections.max.idle.ms", "");
		props.put("enable.auto.commit", kafkaProperties.getEnableAutoCommit());
		props.put("auto.commit.interval.ms", kafkaProperties.getAutoCommitIntervalMs());
		props.put("max.poll.records", kafkaProperties.getMaxPollRecords());

		this.client = client;
		this.messagePack = messagePack;

		consumer = new KafkaConsumer<String, byte[]>(props);
		consumer.subscribe(Collections.singletonList(kafkaProperties.getTopic()));
	}

	@Override
	public void doWork() {
		ConsumerRecords<String, byte[]> consumerRecords = consumer.poll(1000);
		LOGGER.debug("kafka records size : {}", consumerRecords.count());

		for (ConsumerRecord<String, byte[]> consumerRecord : consumerRecords) {
			Message message;
			try {
				message = messagePack.read(consumerRecord.value(), Message.class);
			} catch (Throwable ex) {
				LOGGER.error("failed to read message [{}]", ex.getMessage());
				throw new RuntimeException(ex);
			}

			List<EventData> eventDatas = message.getEventDatas();
			LOGGER.debug("otter eventDatas size : {}", eventDatas.size());

			if (CollectionUtils.isNotEmpty(eventDatas)) {
				for (EventData eventData : eventDatas) {
					if (eventData.getEventType().isInsert() || eventData.getEventType().isUpdate()) {
						List<EventColumn> eventColumns = eventData.getKeys();

						StringBuilder pkNames = new StringBuilder();
						StringBuilder pkValues = new StringBuilder();
						for (EventColumn eventColumn : eventColumns) {
							pkNames.append(eventColumn.getColumnName() + "-");
							pkValues.append(eventColumn.getColumnValue() + "-");
						}

						String pkName = StringUtils.chop(pkNames.toString());
						String pkValue = StringUtils.chop(pkValues.toString());

						IndexRequestBuilder indexRequestBuilder = client.prepareIndex(eventData.getSchemaName(), eventData.getTableName(), pkValue);

						LOGGER.debug("event type : {}", eventData.getEventType().getValue());
						LOGGER.debug("pk name : {}, pk value : {}", pkName, pkValue);

						// TODO Anders 此处需要删除
						if (pkCache.contains(pkValue)) {
							LOGGER.debug("pk is exist : {}", pkValue);
							// throw new RuntimeException();
						} else {
							pkCache.add(pkValue);
						}

						try {
							eventColumns.addAll(eventData.getColumns());
							if (eventColumns.size() > 0) {
								XContentBuilder xContentBuilder = jsonBuilder().startObject();
								for (EventColumn eventColumn : eventColumns) {
									String fieldValue = eventColumn.getColumnValue();
									if (StringUtils.isNotBlank(fieldValue)) {
										if (eventColumn.getColumnType() == Types.DATE || eventColumn.getColumnType() == Types.TIMESTAMP) {
											Date date;
											try {
												date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue);
											} catch (Throwable ex) {
												date = new SimpleDateFormat("yyyy-MM-dd").parse(fieldValue);
											}
											xContentBuilder.field(eventColumn.getColumnName(), date);
										} else {
											xContentBuilder.field(eventColumn.getColumnName(), fieldValue);
										}

									}

									LOGGER.debug("colnum name : {}, colnum value : {}", eventColumn.getColumnName(), fieldValue);
								}

								// IndexResponse indexResponse = indexRequestBuilder.setSource(xContentBuilder.endObject()).get();
								indexRequestBuilder.setSource(xContentBuilder.endObject()).get();
							}
						} catch (Throwable ex) {
							LOGGER.error("failed to put data to es [{}]", ex.getMessage());
							throw new RuntimeException(ex);
						}
					} else if (eventData.getEventType().isDelete()) {
						List<EventColumn> eventColumns = eventData.getKeys();

						DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete(eventData.getSchemaName(), eventData.getTableName(), eventColumns.get(0).getColumnValue());

						LOGGER.debug("event type : {}", eventData.getEventType().getValue());
						LOGGER.debug("pk name : {}, pk value : {}", eventColumns.get(0).getColumnName(), eventColumns.get(0).getColumnValue());

						try {
							// DeleteResponse deleteResponse = deleteRequestBuilder.get();
							deleteRequestBuilder.get();
						} catch (Throwable ex) {
							LOGGER.error("failed to delete data from es [{}]", ex.getMessage());
							throw new RuntimeException(ex);
						}
					} else {
						LOGGER.error("can not support the event type [{}]", eventData.getEventType().getValue());
						throw new RuntimeException("can not support the event type [" + eventData.getEventType().getValue() + "]");
					}

					LOGGER.debug("sql : {}", eventData.getSql());
				}
			}
		}
	}

	@Override
	public String name() {
		return "otterConsumer";
	}

	@Override
	public boolean isInterruptible() {
		return false;
	}
}
