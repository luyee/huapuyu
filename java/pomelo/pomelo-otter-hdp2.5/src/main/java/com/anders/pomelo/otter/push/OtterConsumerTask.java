package com.anders.pomelo.otter.push;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.otter.node.etl.load.loader.db.Message;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;

public class OtterConsumerTask implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(OtterConsumerTask.class);

	private KafkaStream<byte[], byte[]> stream;
	private Connection connection;
	private Admin admin;
	private MessagePack messagePack;

	private final CopyOnWriteArraySet<String> tableNameCache = new CopyOnWriteArraySet<String>();

	public OtterConsumerTask(KafkaStream<byte[], byte[]> stream, Connection connection, Admin admin, MessagePack messagePack) throws IOException {
		this.stream = stream;
		this.connection = connection;
		this.admin = admin;
		this.messagePack = messagePack;
	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		while (it.hasNext()) {
			byte[] content = it.next().message();

			List<EventData> eventDatas = null;
			Message message;
			try {
				message = messagePack.read(content, Message.class);
			} catch (Throwable ex) {
				LOGGER.error("failed to read message [{}]", ex.getMessage());
				throw new RuntimeException(ex);
			}
			eventDatas = message.getEventDatas();

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
}
