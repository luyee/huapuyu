package csvtest;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class PropUtils {
	private static Log log = LogFactory.getLog(PropUtils.class);

	private final static String FILE_NAME = "config.properties";
	private final static Properties PROP = new Properties();

	static {
		try {
			InputStream in = PropUtils.class.getClassLoader().getResourceAsStream(FILE_NAME);
			PROP.load(in);
			in.close();
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public static String readProp(String name) {
		return PROP.getProperty(name);
	}

	public static String readMySqlUrl() {
		return readProp("mysql.url");
	}

	public static String readMySqlUser() {
		return readProp("mysql.user");
	}

	public static String readMySqlPwd() {
		return readProp("mysql.pwd");
	}

	public static String readHSqlDbUrl() {
		return readProp("hsqldb.url");
	}

	public static String readHSqlDbUser() {
		return readProp("hsqldb.user");
	}

	public static String readHSqlDbPwd() {
		return readProp("hsqldb.pwd");
	}

	public static String readSqlFilePath() {
		return readProp("sql.file.path");
	}
}
