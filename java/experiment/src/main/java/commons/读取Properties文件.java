package commons;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

public class 读取Properties文件 {

	@Test
	public void test() throws ConfigurationException {
		PropertiesConfiguration pc = new PropertiesConfiguration("config.properties");
		System.out.println(pc.getProperty("name"));
		pc.setProperty("wife", "guolili");
		pc.save();
		System.out.println(pc.getProperty("wife"));
	}

}
