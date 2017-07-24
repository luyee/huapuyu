package com.anders.ethan.databus.mysqlbinlogconnectorjava;

import java.io.IOException;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.BinaryLogClient.EventListener;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

public class MainTest {

	public static void main(String[] args) throws IOException {
		BinaryLogClient client = new BinaryLogClient("192.168.56.121", 3306, "root", "123");
		client.setBinlogFilename("mysql-bin.000447");
		client.setBinlogPosition(3296);
		
		client.registerEventListener(new EventListener() {
			@Override
			public void onEvent(Event event) {
				System.out.println(event.getHeader().getClass() + " : " + event.getData().getClass());
				if (event.getData() instanceof QueryEventData) {
					QueryEventData queryEventData = event.getData();
					System.out.println(queryEventData.getSql());
				} else if (event.getData() instanceof UpdateRowsEventData) {
					UpdateRowsEventData updateRowsEventData = event.getData();
					System.out.println(updateRowsEventData.getIncludedColumns());
				}else if (event.getData() instanceof DeleteRowsEventData) {
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
		client.connect();

		System.in.read();
	}

}
