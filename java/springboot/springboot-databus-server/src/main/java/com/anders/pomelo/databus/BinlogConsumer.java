package com.anders.pomelo.databus;

import java.io.IOException;

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

@Component
public class BinlogConsumer implements DisposableBean {

	private static Logger LOGGER = LoggerFactory.getLogger(BinlogConsumer.class);

	private BinaryLogClient binaryLogClient;

	@Autowired
	private BinlogProps mySQLProps;
	@Autowired
	private DatabaseMetadata databaseMetadata;

	@Override
	public void destroy() throws Exception {
		if (binaryLogClient != null && binaryLogClient.isConnected()) {
			binaryLogClient.disconnect();
		}
	}

	public void start() throws IOException {
		databaseMetadata.genMetadata();

		binaryLogClient = new BinaryLogClient(mySQLProps.getHost(), mySQLProps.getPort(), mySQLProps.getUsername(),
				mySQLProps.getPassword());

		binaryLogClient.setBinlogFilename(mySQLProps.getFilename());
		binaryLogClient.setBinlogPosition(mySQLProps.getPosition());
		binaryLogClient.registerEventListener(new EventListener() {
			@Override
			public void onEvent(Event event) {
				System.out.println(event.getHeader().getClass() + " : " + event.getData().getClass());
				if (event.getData() instanceof QueryEventData) {
					QueryEventData queryEventData = event.getData();
					System.out.println(queryEventData.getSql());
				} else if (event.getData() instanceof UpdateRowsEventData) {
					UpdateRowsEventData updateRowsEventData = event.getData();
					System.out.println(updateRowsEventData.getIncludedColumns());
				} else if (event.getData() instanceof DeleteRowsEventData) {
					DeleteRowsEventData deleteRowsEventData = event.getData();
					System.out.println(deleteRowsEventData.getIncludedColumns());
				} else if (event.getData() instanceof WriteRowsEventData) {
					WriteRowsEventData writeRowsEventData = event.getData();
					System.out.println(writeRowsEventData.getIncludedColumns());
				} else if (event.getData() instanceof TableMapEventData) {
					TableMapEventData tableMapEventData = event.getData();
					System.out.println(tableMapEventData.getColumnNullability());
				} else {
				}
			}
		});
		binaryLogClient.connect();
	}

}
