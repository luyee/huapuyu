package commons;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

public class 读取Properties文件 {

	@Test
	public void test() throws ConfigurationException {
		// PropertiesConfiguration pc = new PropertiesConfiguration("config.properties");
		System.out.println(this.getClass().getResource("config.properties"));
		PropertiesConfiguration pc = new PropertiesConfiguration(this.getClass().getResource("config.properties"));
		pc.setAutoSave(true);
		System.out.println(pc.getProperty("name"));
		pc.setProperty("wife", "guolili");
		// D:/code/java/experiment/target/classes/commons/config.properties才是真正修改的文件
		pc.save();
		// pc.save(this.getClass().getResource("config.properties"));
		System.out.println(pc.getProperty("wife"));
	}

}
