package csvtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class MySqlUtils {
	private static Log log = LogFactory.getLog(MySqlUtils.class);

	private final static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			if (threadLocal.get() == null || threadLocal.get().isClosed()) {
				conn = DriverManager.getConnection(PropUtils.readMySqlUrl(), PropUtils.readMySqlUser(), PropUtils.readMySqlPwd());
				threadLocal.set(conn);
				log.debug("创建MySQL数据库连接");
				return conn;
			}
			conn = threadLocal.get();
		}
		catch (SQLException e) {
			log.error(e.getMessage());
		}
		log.debug("缓存中获取MySQL数据库连接");
		return conn;
	}
}
