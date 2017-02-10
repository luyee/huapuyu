package 语言;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class 异常 {

	private static Logger logger = LoggerFactory.getLogger(异常.class);

	public static void main(String[] args) {
		int i = 5;
		int j = 0;

		try {
			i = i / j;
		}
		catch (Throwable e) {
			logger.error(e.getMessage());
			logger.error("error ", e.getMessage());
			// 错误信息输出到{}中
			logger.error("error {}", e.getMessage());
		}
	}
}
