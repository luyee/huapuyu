package com.anders.pomelo.otter.poll;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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

import kafka.utils.ShutdownableThread;

public class ConsumerThread extends ShutdownableThread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerThread.class);

	private final KafkaConsumer<String, byte[]> consumer;
	private final TransportClient client;
	private final MessagePack messagePack;
	private final SimpleDateFormat ymdthmszz = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");
	private final SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	private final String index;

	public ConsumerThread(KafkaProps kafkaProperties, TransportClient client, MessagePack messagePack, String index) {
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
		this.index = index;

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

						IndexRequestBuilder indexRequestBuilder = client.prepareIndex(this.index, eventData.getTableName(), pkValue);

						LOGGER.debug("event type : {}, pk name : {}, pk value : {}", eventData.getEventType().getValue(), pkName, pkValue);

						try {
							eventColumns.addAll(eventData.getColumns());
							if (eventColumns.size() > 0) {
								XContentBuilder xContentBuilder = jsonBuilder().startObject();
								for (EventColumn eventColumn : eventColumns) {
									String fieldValue = eventColumn.getColumnValue();
									if (StringUtils.isNotBlank(fieldValue)) {
										if (eventColumn.getColumnType() == Types.DATE || eventColumn.getColumnType() == Types.TIMESTAMP) {
											String dateString = null;
											try {
												Date date = ymdhms.parse(fieldValue);
												dateString = ymdthmszz.format(date);
											} catch (Throwable ex) {
												Date date = ymd.parse(fieldValue);
												dateString = ymd.format(date);
											}
											xContentBuilder.field(eventColumn.getColumnName(), dateString);
										} else if (eventColumn.getColumnType() == Types.INTEGER) {
											xContentBuilder.field(eventColumn.getColumnName(), Integer.parseInt(fieldValue));
										} else if (eventColumn.getColumnType() == Types.BIGINT) {
											xContentBuilder.field(eventColumn.getColumnName(), Long.parseLong(fieldValue));
										} else if (eventColumn.getColumnType() == Types.TINYINT) {
											xContentBuilder.field(eventColumn.getColumnName(), Short.parseShort(fieldValue));
										} else if (eventColumn.getColumnType() == Types.FLOAT) {
											xContentBuilder.field(eventColumn.getColumnName(), Float.parseFloat(fieldValue));
										} else if (eventColumn.getColumnType() == Types.DOUBLE) {
											xContentBuilder.field(eventColumn.getColumnName(), Double.parseDouble(fieldValue));
										} else {
											xContentBuilder.field(eventColumn.getColumnName(), fieldValue);
										}
									} else {
										xContentBuilder.field(eventColumn.getColumnName(), StringUtils.EMPTY);
									}

									LOGGER.debug("colnum name : {}, colnum value : {}", eventColumn.getColumnName(), fieldValue);
								}

								// IndexResponse indexResponse =
								// indexRequestBuilder.setSource(xContentBuilder.endObject()).get();
								indexRequestBuilder.setSource(xContentBuilder.endObject()).get();
							}
						} catch (Throwable ex) {
							LOGGER.error("failed to put data to es [{}]", ex.getMessage());
							throw new RuntimeException(ex);
						}
					} else if (eventData.getEventType().isDelete()) {
						List<EventColumn> eventColumns = eventData.getKeys();

						StringBuilder pkNames = new StringBuilder();
						StringBuilder pkValues = new StringBuilder();
						for (EventColumn eventColumn : eventColumns) {
							pkNames.append(eventColumn.getColumnName() + "-");
							pkValues.append(eventColumn.getColumnValue() + "-");
						}

						String pkName = StringUtils.chop(pkNames.toString());
						String pkValue = StringUtils.chop(pkValues.toString());

						LOGGER.debug("event type : {}, pk name : {}, pk value : {}", eventData.getEventType().getValue(), pkName, pkValue);

						DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete(this.index, eventData.getTableName(), pkValue);

						try {
							// DeleteResponse deleteResponse =
							// deleteRequestBuilder.get();
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
