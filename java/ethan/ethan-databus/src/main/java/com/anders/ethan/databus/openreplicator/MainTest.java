package com.anders.ethan.databus.openreplicator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.code.or.OpenReplicator;
import com.google.code.or.binlog.BinlogEventListener;
import com.google.code.or.binlog.BinlogEventV4;
import com.google.code.or.binlog.impl.event.FormatDescriptionEvent;
import com.google.code.or.binlog.impl.event.QueryEvent;
import com.google.code.or.binlog.impl.event.RotateEvent;
import com.google.code.or.binlog.impl.event.WriteRowsEventV2;
import com.google.code.or.binlog.impl.event.XidEvent;
import com.google.code.or.common.glossary.Column;
import com.google.code.or.common.glossary.Row;

public class MainTest {
	public static void main(String[] args) throws Exception {
		final OpenReplicator or = new OpenReplicator();
		or.setUser("anders");
		or.setPassword("123");
		or.setHost("192.168.56.101");
		or.setPort(3306);
		or.setServerId(8394);
		or.setBinlogPosition(120);
		or.setBinlogFileName("mysql-bin.000002");
		or.setBinlogEventListener(new BinlogEventListener() {
			public void onEvents(BinlogEventV4 event) {
				System.out.println("*****************************");
				System.out.println(event.getClass());
				System.out.println("*****************************");
				if (event instanceof QueryEvent) {
					QueryEvent queryEvent = (QueryEvent) event;
					System.out.println(queryEvent.getErrorCode());
					System.out.println(queryEvent.getThreadId());
					System.out.println(getChars(queryEvent.getDatabaseName()
							.getValue()));
					System.out
							.println(getChars(queryEvent.getSql().getValue()));
				} else if (event instanceof XidEvent) {
					XidEvent xidEvent = (XidEvent) event;
					System.out.println(xidEvent.getXid());
				} else if (event instanceof FormatDescriptionEvent) {
					FormatDescriptionEvent formatDescriptionEvent = (FormatDescriptionEvent) event;
					System.out.println(formatDescriptionEvent
							.getBinlogVersion());
					System.out.println(formatDescriptionEvent
							.getCreateTimestamp());
					// System.out.println(getChars(formatDescriptionEvent.getEventTypes()));
					System.out.println(getChars(formatDescriptionEvent
							.getServerVersion().getValue()));
				} else if (event instanceof RotateEvent) {
					RotateEvent rotateEvent = (RotateEvent) event;
					System.out.println(rotateEvent.getBinlogPosition());
					System.out.println(getChars(rotateEvent
							.getBinlogFileName().getValue()));
				} else if (event instanceof WriteRowsEventV2) {
					WriteRowsEventV2 writeRowsEventV2 = (WriteRowsEventV2) event;
					List<Row> rows = writeRowsEventV2.getRows();
					System.out.println(writeRowsEventV2.getReserved());
					System.out.println(writeRowsEventV2.getTableId());
					System.out.println(writeRowsEventV2.getUsedColumns());
					System.out.println(writeRowsEventV2.getColumnCount());
					for (Row row:rows) {
						List<Column> columns = row.getColumns();
						for (Column column : columns) {
							System.out.println(column.getClass() + ":" + column.getValue());
						}
					}
				}
			}
		});
		or.start();

		System.out.println("press 'q' to stop");
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			if (line.equals("q")) {
				or.stop(1, TimeUnit.SECONDS);
				break;
			}
		}
	}

	private static char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);
		return cb.array();
	}
}
