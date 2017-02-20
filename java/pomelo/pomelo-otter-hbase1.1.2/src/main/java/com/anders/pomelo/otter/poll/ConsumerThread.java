package com.anders.pomelo.otter.poll;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArraySet;

import kafka.utils.ShutdownableThread;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
	private final Connection connection;
	private final Admin admin;
	private final MessagePack messagePack;
	// TODO Anders 下面代码需要删除
	private final CopyOnWriteArraySet<String> tableNameCache = new CopyOnWriteArraySet<String>();

	public ConsumerThread(KafkaProps kafkaProperties, Connection connection, Admin admin, MessagePack messagePack) {
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

		this.connection = connection;
		this.admin = admin;
		this.messagePack = messagePack;

		consumer = new KafkaConsumer<String, byte[]>(props);
		consumer.subscribe(Collections.singletonList(kafkaProperties.getTopic()));
	}

	@Override
	public void doWork() {
		ConsumerRecords<String, byte[]> consumerRecords = consumer.poll(1000);
		System.out.println("************************************** records : " + consumerRecords.count());

		for (ConsumerRecord<String, byte[]> consumerRecord : consumerRecords) {
			Message message;
			try {
				message = messagePack.read(consumerRecord.value(), Message.class);
			} catch (Throwable ex) {
				LOGGER.error("failed to read message [{}]", ex.getMessage());
				throw new RuntimeException(ex);
			}

			List<EventData> eventDatas = message.getEventDatas();
			System.out.println("************************************** eventDatas : " + eventDatas.size());

			if (CollectionUtils.isNotEmpty(eventDatas)) {
				for (EventData eventData : eventDatas) {
					TableName tableName = TableName.valueOf(eventData.getSchemaName() + ":" + eventData.getTableName());

					try {
						if (!admin.tableExists(tableName)) {
							HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
							hTableDescriptor.addFamily(new HColumnDescriptor("source"));
							admin.createTable(hTableDescriptor);
						}
					} catch (Throwable ex) {
						LOGGER.error("failed to create table [{}]", ex.getMessage());
						throw new RuntimeException(ex);
					}

					Table table;
					try {
						table = connection.getTable(tableName);
					} catch (Throwable ex) {
						LOGGER.error("failed to get table name [{}]", ex.getMessage());
						throw new RuntimeException(ex);
					}

					if (eventData.getEventType().isInsert() || eventData.getEventType().isUpdate()) {
						List<EventColumn> eventColumns = eventData.getKeys();
						Put put = new Put(Bytes.toBytes(eventColumns.get(0).getColumnValue()));

						LOGGER.debug("event type : {}", eventData.getEventType().getValue());
						LOGGER.error("rowkey name : {}, rowkey value : {}", eventColumns.get(0).getColumnName(), eventColumns.get(0).getColumnValue());

						// TODO Anders 此处需要删除
						if (tableNameCache.contains(eventColumns.get(0).getColumnValue())) {
							LOGGER.error("rowkey is exist : {}", eventColumns.get(0).getColumnValue());
							// throw new RuntimeException();
						} else {
							tableNameCache.add(eventColumns.get(0).getColumnValue());
						}

						eventColumns = eventData.getColumns();
						if (eventColumns.size() > 0) {
							for (EventColumn eventColumn : eventColumns) {
								String fieldValue = eventColumn.getColumnValue();
								if (fieldValue == null) {
									fieldValue = StringUtils.EMPTY;
								}
								put.addColumn(Bytes.toBytes("source"), Bytes.toBytes(eventColumn.getColumnName()), Bytes.toBytes(fieldValue));

								LOGGER.debug("colnum name : {}, colnum value : {}", eventColumn.getColumnName(), fieldValue);
							}
							try {
								table.put(put);
							} catch (Throwable ex) {
								LOGGER.error("failed to put data to hbase [{}]", ex.getMessage());
								throw new RuntimeException(ex);
							}
						}
					} else if (eventData.getEventType().isDelete()) {
						List<EventColumn> eventColumns = eventData.getKeys();
						Delete delete = new Delete(Bytes.toBytes(eventColumns.get(0).getColumnValue()));

						LOGGER.debug("event type : {}", eventData.getEventType().getValue());
						LOGGER.debug("rowkey name : {}, rowkey value : {}", eventColumns.get(0).getColumnName(), eventColumns.get(0).getColumnValue());

						try {
							table.delete(delete);
						} catch (Throwable ex) {
							LOGGER.error("failed to delete data from hbase [{}]", ex.getMessage());
							throw new RuntimeException(ex);
						}
					} else {
						LOGGER.error("can not support the event type [{}]", eventData.getEventType().getValue());
						throw new RuntimeException("can not support the event type [" + eventData.getEventType().getValue() + "]");
					}

					LOGGER.error("sql : {}", eventData.getSql());
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
