import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GlusterFSTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWrite() throws IOException {
		// static void:write(File file, CharSequence data, String encoding,
		// boolean append)
		FileUtils.write(new File("/tmp/gfstest/java/cxyapi.txt"), "程序换api", "UTF-8", true);

		// static void:writeLines(File file, Collection<?> lines, boolean
		// append)
		List<String> lines = new ArrayList<String>();
		lines.add("欢迎访问:");
		lines.add("www.cxyapi.com");
		FileUtils.writeLines(new File("/tmp/gfstest/java/cxyapi.txt"), lines, true);

		// static void:writeStringToFile(File file, String data, String
		// encoding, boolean append)
		FileUtils.writeStringToFile(new File("/tmp/gfstest/java/cxyapi.txt"), "作者：cxy", "UTF-8", true);
	}

	@Test
	public void testRead() throws IOException {
		// 读文件
		// static String:readFileToString(File file, String encoding)
		System.out.println(FileUtils.readFileToString(new File("/tmp/gfstest/java/cxyapi.txt"), "UTF-8"));

		// static List<String>:readLines(File file, String encoding)
		System.out.println(FileUtils.readLines(new File("/tmp/gfstest/java/cxyapi.txt"), "UTF-8")); // 返回一个list
	}

}
