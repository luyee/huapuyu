package com.anders.pomelo.databus;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anders.pomelo.databus.cfg.MySQLProps;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.BinaryLogClient.EventListener;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

@Component
public class BinlogConsumer implements InitializingBean, DisposableBean {

	private BinaryLogClient binaryLogClient;

	@Autowired
	private MySQLProps mySQLProps;

	@Override
	public void destroy() throws Exception {
		if (binaryLogClient != null && binaryLogClient.isConnected()) {
			binaryLogClient.disconnect();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		binaryLogClient = new BinaryLogClient(mySQLProps.getHost(), mySQLProps.getPort(), mySQLProps.getUsername(),
				mySQLProps.getPassword());

		binaryLogClient.setBinlogFilename("mysql-bin.000447");
		binaryLogClient.setBinlogPosition(3296);
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
