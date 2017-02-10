package csvtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class HSqlDbUtils {
	private static Log log = LogFactory.getLog(HSqlDbUtils.class);

	private final static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	static {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		}
		catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			if (threadLocal.get() == null || threadLocal.get().isClosed()) {
				conn = DriverManager.getConnection(PropUtils.readHSqlDbUrl(), PropUtils.readHSqlDbUser(), PropUtils.readHSqlDbPwd());
				threadLocal.set(conn);
				log.debug("创建HSQLDB数据库连接");
				return conn;
			}
			conn = threadLocal.get();
		}
		catch (SQLException e) {
			log.error(e.getMessage());
		}
		log.debug("缓存中获取HSQLDB数据库连接");
		return conn;
	}
}
