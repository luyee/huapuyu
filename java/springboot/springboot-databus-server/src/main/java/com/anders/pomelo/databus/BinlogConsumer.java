package com.anders.pomelo.databus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anders.pomelo.databus.cfg.BinlogProps;
import com.anders.pomelo.databus.cfg.SlavedbProps;
import com.anders.pomelo.databus.handler.DeleteRowsEventDataHandler;
import com.anders.pomelo.databus.handler.QueryEventDataHandler;
import com.anders.pomelo.databus.handler.UpdateRowsEventDataHandler;
import com.anders.pomelo.databus.handler.WriteRowsEventDataHandler;
import com.anders.pomelo.databus.model.Schema;
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
	private SlavedbProps slavedbProps;
	@Autowired
	private DatabaseMetadata databaseMetadata;
	@Autowired
	private WriteRowsEventDataHandler writeRowsEventDataHandler;
	@Autowired
	private DeleteRowsEventDataHandler deleteRowsEventDataHandler;
	@Autowired
	private UpdateRowsEventDataHandler updateRowsEventDataHandler;
	@Autowired
	private QueryEventDataHandler queryEventDataHandler;

	private Schema schema;

	@Override
	public void destroy() throws Exception {
		if (binaryLogClient != null && binaryLogClient.isConnected()) {
			binaryLogClient.disconnect();
		}
	}

	public void start() throws IOException, SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(slavedbProps.getUrl(), slavedbProps.getUsername(), slavedbProps.getPassword());

		schema = databaseMetadata.generate();

		binaryLogClient = new BinaryLogClient(binlogProps.getHost(), binlogProps.getPort(), binlogProps.getUsername(), binlogProps.getPassword());
		binaryLogClient.setBinlogFilename(binlogProps.getFilename());
		binaryLogClient.setBinlogPosition(binlogProps.getPosition());
		binaryLogClient.registerEventListener(new EventListener() {
			@Override
			public void onEvent(Event event) {
				LOGGER.warn("{}:{}:{} {}:{}", event.getHeader().getServerId(), binaryLogClient.getBinlogFilename(), binaryLogClient.getBinlogPosition(), event.getHeader().getClass(), event.getHeader().getEventType());

				if (event.getData() instanceof QueryEventData) {
					QueryEventData queryEventData = event.getData();
					if (binlogProps.getIncludedDatabases().contains(queryEventData.getDatabase())) {
						try {
							queryEventDataHandler.execute(queryEventData, schema, connection);
						} catch (SQLException e) {
							// throw new RuntimeException(e);
							try {
								binaryLogClient.disconnect();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						schema = databaseMetadata.generate();
					}
				} else if (event.getData() instanceof UpdateRowsEventData) {
					UpdateRowsEventData updateRowsEventData = event.getData();
					// System.out.println(updateRowsEventData.getIncludedColumns());
					// System.out.println(updateRowsEventData.getIncludedColumnsBeforeUpdate());
					// System.out.println(updateRowsEventData.getTableId());
					// for (Map.Entry<Serializable[], Serializable[]> entry :
					// updateRowsEventData.getRows()) {
					// for (Serializable s : entry.getKey()) {
					// if (s != null) {
					// if (s instanceof String) {
					// System.out.println(s.getClass() + " " + (String) s);
					// } else if (s instanceof Integer) {
					// System.out.println(s.getClass() + " " + (Integer) s);
					// } else {
					// System.out.println(s.getClass());
					// }
					// } else {
					// System.out.println("null");
					// }
					// }
					// System.out.println("---------------");
					// for (Serializable s : entry.getValue()) {
					// if (s != null) {
					// if (s instanceof String) {
					// System.out.println(s.getClass() + " " + (String) s);
					// } else if (s instanceof Integer) {
					// System.out.println(s.getClass() + " " + (Integer) s);
					// } else {
					// System.out.println(s.getClass());
					// }
					// } else {
					// System.out.println("null");
					// }
					// }
					// System.out.println("===============");
					// }
					try {
						updateRowsEventDataHandler.execute(updateRowsEventData, schema, connection);
					} catch (SQLException e) {
						// throw new RuntimeException(e);
						try {
							binaryLogClient.disconnect();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} else if (event.getData() instanceof DeleteRowsEventData) {
					DeleteRowsEventData deleteRowsEventData = event.getData();
					// System.out.println(deleteRowsEventData.getIncludedColumns());
					// System.out.println(deleteRowsEventData.getTableId());
					// for (Serializable[] serializable :
					// deleteRowsEventData.getRows()) {
					// for (Serializable s : serializable) {
					// if (s != null) {
					// if (s instanceof String) {
					// System.out.println(s.getClass() + " " + (String) s);
					// } else if (s instanceof Integer) {
					// System.out.println(s.getClass() + " " + (Integer) s);
					// } else {
					// System.out.println(s.getClass());
					// }
					// } else {
					// System.out.println("null");
					// }
					// }
					// }
					try {
						deleteRowsEventDataHandler.execute(deleteRowsEventData, schema, connection);
					} catch (SQLException e) {
						// throw new RuntimeException(e);
						try {
							binaryLogClient.disconnect();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} else if (event.getData() instanceof WriteRowsEventData) {
					WriteRowsEventData writeRowsEventData = event.getData();
					// BitSet bitSet = writeRowsEventData.getIncludedColumns();
					// for (int i = 0; i < bitSet.length(); i++) {
					// System.out.println(bitSet.get(i));
					// }
					// System.out.println(writeRowsEventData.getTableId());
					// for (Serializable[] serializable :
					// writeRowsEventData.getRows()) {
					// for (Serializable s : serializable) {
					// if (s instanceof String) {
					// System.out.println(s.getClass() + " " + (String) s);
					// } else if (s instanceof Integer) {
					// System.out.println(s.getClass() + " " + (Integer) s);
					// } else if (s == null) {
					// System.out.println("null");
					// } else {
					// System.out.println(s.getClass());
					// }
					// }
					// }
					try {
						writeRowsEventDataHandler.execute(writeRowsEventData, schema, connection);
					} catch (SQLException e) {
						// throw new RuntimeException(e);
						try {
							binaryLogClient.disconnect();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} else if (event.getData() instanceof TableMapEventData) {
					TableMapEventData tableMapEventData = event.getData();
					// System.out.println(tableMapEventData.getDatabase());
					// System.out.println(tableMapEventData.getTable());
					// System.out.println(tableMapEventData.getTableId());
					// for (int i : tableMapEventData.getColumnMetadata()) {
					// System.out.println(i);
					// }
					// System.out.println(tableMapEventData.getColumnNullability());
					// for (byte i : tableMapEventData.getColumnTypes()) {
					// System.out.println(i);
					// }

					schema.addTableId(tableMapEventData.getTableId(), tableMapEventData.getDatabase(), tableMapEventData.getTable());
				} else if (event.getData() instanceof XidEventData) {
					// XidEventData xidEventData = event.getData();
					// System.out.println(xidEventData.getXid());
				} else {
					// System.out.println(event.getClass());
				}
			}
		});
		binaryLogClient.connect();
	}

}
