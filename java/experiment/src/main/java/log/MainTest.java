package log;

import org.apache.log4j.Logger;

public class MainTest {

	private static final Logger logger = Logger.getLogger(MainTest.class);

	public static void main(String[] args) {
		logger.info("此日志会在日志文件中显示");
		new LogTest().print();
	}

}
