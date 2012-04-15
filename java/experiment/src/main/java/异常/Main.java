package 异常;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		int i = 5;
		int j = 0;

		try {
			i = i / j;
		}
		catch (Exception e) {
			logger.info(e.getMessage());
			logger.info("error ", e.getMessage());
			// 错误信息输出到{}中
			logger.info("error {}", e.getMessage());
		}
	}
}
