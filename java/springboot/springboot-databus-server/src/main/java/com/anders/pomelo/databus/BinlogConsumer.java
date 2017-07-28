package com.anders.pomelo.databus;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anders.pomelo.databus.cfg.BinlogProps;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.BinaryLogClient.EventListener;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.github.shyiko.mysql.binlog.event.XidEventData;

@Component
public class BinlogConsumer implements DisposableBean {

	private static Logger LOGGER = LoggerFactory.getLogger(BinlogConsumer.class);

	private BinaryLogClient binaryLogClient;

	@Autowired
	private BinlogProps binlogProps;
	@Autowired
	private DatabaseMetadata databaseMetadata;

	@Override
	public void destroy() throws Exception {
		if (binaryLogClient != null && binaryLogClient.isConnected()) {
			binaryLogClient.disconnect();
		}
	}

	public void start() throws IOException {
		 databaseMetadata.genDatabases();

		binaryLogClient = new BinaryLogClient(binlogProps.getHost(), binlogProps.getPort(), binlogProps.getUsername(),
				binlogProps.getPassword());

		binaryLogClient.setBinlogFilename(binlogProps.getFilename());
		binaryLogClient.setBinlogPosition(binlogProps.getPosition());
		binaryLogClient.registerEventListener(new EventListener() {
			@Override
			public void onEvent(Event event) {
				System.out.println("*****************************begin****************************88");
				System.out.println(event.getHeader().getClass() + " : " + event.getData().getClass());
				if (event.getData() instanceof QueryEventData) {
					QueryEventData queryEventData = event.getData();
					System.out.println(queryEventData.getSql());
				} else if (event.getData() instanceof UpdateRowsEventData) {
					UpdateRowsEventData updateRowsEventData = event.getData();
					System.out.println(updateRowsEventData.getIncludedColumns());
					System.out.println(updateRowsEventData.getIncludedColumnsBeforeUpdate());
					System.out.println(updateRowsEventData.getTableId());
					for (Map.Entry<Serializable[], Serializable[]> entry : updateRowsEventData.getRows()) {
						for (Serializable s : entry.getKey()) {
							if (s instanceof String) {
								System.out.println(s.getClass() + " " + (String) s);
							} else if (s instanceof Integer) {
								System.out.println(s.getClass() + " " + (Integer) s);
							} else {
								System.out.println(s.getClass());
							}
						}
						for (Serializable s : entry.getValue()) {
							if (s instanceof String) {
								System.out.println(s.getClass() + " " + (String) s);
							} else if (s instanceof Integer) {
								System.out.println(s.getClass() + " " + (Integer) s);
							} else {
								System.out.println(s.getClass());
							}
						}
					}
				} else if (event.getData() instanceof DeleteRowsEventData) {
					DeleteRowsEventData deleteRowsEventData = event.getData();
					System.out.println(deleteRowsEventData.getIncludedColumns());
					System.out.println(deleteRowsEventData.getTableId());
					for (Serializable[] serializable : deleteRowsEventData.getRows()) {
						for (Serializable s : serializable) {
							if (s instanceof String) {
								System.out.println(s.getClass() + " " + (String) s);
							} else if (s instanceof Integer) {
								System.out.println(s.getClass() + " " + (Integer) s);
							} else {
								System.out.println(s.getClass());
							}
						}
					}
				} else if (event.getData() instanceof WriteRowsEventData) {
					WriteRowsEventData writeRowsEventData = event.getData();
					System.out.println(writeRowsEventData.getIncludedColumns());
					System.out.println(writeRowsEventData.getTableId());
					for (Serializable[] serializable : writeRowsEventData.getRows()) {
						for (Serializable s : serializable) {
							if (s instanceof String) {
								System.out.println(s.getClass() + " " + (String) s);
							} else if (s instanceof Integer) {
								System.out.println(s.getClass() + " " + (Integer) s);
							} else {
								System.out.println(s.getClass());
							}
						}
					}
				} else if (event.getData() instanceof TableMapEventData) {
					TableMapEventData tableMapEventData = event.getData();
					System.out.println(tableMapEventData.getDatabase());
					System.out.println(tableMapEventData.getTable());
					System.out.println(tableMapEventData.getTableId());
					for (int i : tableMapEventData.getColumnMetadata()) {
						System.out.println(i);
					}
					System.out.println(tableMapEventData.getColumnNullability());
					for (byte i : tableMapEventData.getColumnTypes()) {
						System.out.println(i);
					}
				} else if (event.getData() instanceof XidEventData) {
					XidEventData xidEventData = event.getData();
					System.out.println(xidEventData.getXid());
				} else {
					System.out.println(event.getClass());
				}
			}
		});
		binaryLogClient.connect();
	}

}
