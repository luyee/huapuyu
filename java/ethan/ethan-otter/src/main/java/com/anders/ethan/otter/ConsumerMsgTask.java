package com.anders.ethan.otter;

import java.io.IOException;
import java.util.List;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.msgpack.MessagePack;

import com.alibaba.otter.node.etl.load.loader.db.Message;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncConsistency;
import com.alibaba.otter.shared.common.model.config.channel.ChannelParameter.SyncMode;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import com.alibaba.otter.shared.etl.model.EventType;

public class ConsumerMsgTask implements Runnable {
	private KafkaStream<byte[], byte[]> m_stream;
	private int m_threadNumber;
	private Table table;

	public ConsumerMsgTask(KafkaStream<byte[], byte[]> stream,
			int threadNumber, Table table) throws IOException {
		m_threadNumber = threadNumber;
		m_stream = stream;
		this.table = table;
	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		while (it.hasNext()) {
			byte[] content = it.next().message();
			MessagePack messagePack = new MessagePack();

			messagePack.register(EventType.class);
			messagePack.register(EventColumn.class);
			messagePack.register(SyncMode.class);
			messagePack.register(SyncConsistency.class);
			messagePack.register(EventData.class);
			messagePack.register(Message.class);

			List<EventData> eventDatas = null;
			try {
				Message message = messagePack.read(content, Message.class);
				eventDatas = message.getEventDatas();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (eventDatas != null) {
				for (EventData eventData : eventDatas) {
					if (eventData.getEventType().isInsert()) {
						List<EventColumn> eventColumns = eventData.getKeys();
						Put put = new Put(Bytes.toBytes(eventColumns.get(0)
								.getColumnValue()));

						System.out.println(eventColumns.get(0).getColumnName()
								+ " " + eventColumns.get(0).getColumnValue());

						eventColumns = eventData.getColumns();
						for (EventColumn eventColumn : eventColumns) {
							put.addColumn(Bytes.toBytes("cf"),
									Bytes.toBytes(eventColumn.getColumnName()),
									Bytes.toBytes(eventColumn.getColumnValue()));

							System.out.println(eventColumn.getColumnName()
									+ " " + eventColumn.getColumnValue());
						}
						try {
							table.put(put);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					System.out.println(eventData.getSql());
				}
			}
			System.out.println("commit");

		}
		System.out.println("Shutting down Thread: " + m_threadNumber);
	}
}
