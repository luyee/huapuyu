package com.anders.pomelo.otter.poll;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArraySet;

import kafka.utils.ShutdownableThread;

import org.apache.commons.collections.CollectionUtils;
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

public class Consumer extends ShutdownableThread {

	private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

	private final KafkaConsumer<String, byte[]> consumer;
	private final Connection connection;
	@SuppressWarnings("unused")
	private final Admin admin;
	private final MessagePack messagePack;
	// TODO Anders 下面代码需要删除
	private final CopyOnWriteArraySet<String> tableNameCache = new CopyOnWriteArraySet<String>();

	public Consumer(String broker, String groupId, String topic, Connection connection, Admin admin, MessagePack messagePack) {
		super("otterConsumer", false);

		Properties props = new Properties();
		props.put("bootstrap.servers", broker);
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		// kafkaProps.put("fetch.min.bytes", "1");
		props.put("group.id", groupId);
		// kafkaProps.put("heartbeat.interval.ms", "3000");
		// kafkaProps.put("max.partition.fetch.bytes", "");
		props.put("session.timeout.ms", "30000");
		// kafkaProps.put("auto.offset.reset", "");
		// kafkaProps.put("connections.max.idle.ms", "");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");

		this.connection = connection;
		this.admin = admin;
		this.messagePack = messagePack;

		consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Collections.singletonList(topic));
	}

	@Override
	public void doWork() {
		ConsumerRecords<String, byte[]> consumerRecords = consumer.poll(1000);
		for (ConsumerRecord<String, byte[]> consumerRecord : consumerRecords) {
			Message message;
			try {
				message = messagePack.read(consumerRecord.value(), Message.class);
			} catch (Throwable ex) {
				LOGGER.error("failed to read message [{}]", ex.getMessage());
				throw new RuntimeException(ex);
			}

			List<EventData> eventDatas = message.getEventDatas();

			if (CollectionUtils.isNotEmpty(eventDatas)) {
				for (EventData eventData : eventDatas) {
					TableName tableName = TableName.valueOf(eventData.getTableName());

					// try {
					// if (!admin.tableExists(tableName)) {
					// HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
					// hTableDescriptor.addFamily(new HColumnDescriptor("source"));
					// admin.createTable(hTableDescriptor);
					// }
					// } catch (Throwable ex) {
					// LOGGER.error("failed to create table [{}]", ex.getMessage());
					// throw new RuntimeException(ex);
					// }

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
						LOGGER.debug("rowkey name : {}, rowkey value : {}", eventColumns.get(0).getColumnName(), eventColumns.get(0).getColumnValue());

						// TODO Anders 此处需要删除
						if (tableNameCache.contains(eventColumns.get(0).getColumnValue())) {
							LOGGER.error("rowkey is exist : {}", eventColumns.get(0).getColumnValue());
						} else {
							tableNameCache.add(eventColumns.get(0).getColumnValue());
						}

						eventColumns = eventData.getColumns();
						for (EventColumn eventColumn : eventColumns) {
							put.addColumn(Bytes.toBytes("source"), Bytes.toBytes(eventColumn.getColumnName()), Bytes.toBytes(eventColumn.getColumnValue()));

							LOGGER.debug("colnum name : {}, colnum value : {}", eventColumn.getColumnName(), eventColumn.getColumnValue());
						}
						try {
							table.put(put);
						} catch (Throwable ex) {
							LOGGER.error("failed to put data [{}]", ex.getMessage());
							throw new RuntimeException(ex);
						}
					} else if (eventData.getEventType().isDelete()) {
						List<EventColumn> eventColumns = eventData.getKeys();
						Delete delete = new Delete(Bytes.toBytes(eventColumns.get(0).getColumnValue()));

						LOGGER.debug("event type : {}", eventData.getEventType().getValue());
						LOGGER.debug("rowkey name : {}, rowkey value : {}", eventColumns.get(0).getColumnName(), eventColumns.get(0).getColumnValue());

						try {
							table.delete(delete);
						} catch (Throwable ex) {
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
