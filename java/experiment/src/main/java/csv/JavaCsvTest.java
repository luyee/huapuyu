package csv;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.csvreader.CsvReader;

public class JavaCsvTest {

	@Test
	public void test() throws IOException {
		long beginTime = new Date().getTime();
		String path = getClass().getResource("JavaCsv.csv").getPath();
		System.out.println(path);
		CsvReader reader = new CsvReader(new FileReader(path));

		// String[] header;
		// while (reader.readHeaders()) {
		// System.out.println("--------------header------------");
		// header = reader.getHeaders();
		// System.out.println("Name: [" + header[0] + "]\nAddress: [" + header[1] + "]\nEmail: [" + header[2] + "]");
		// }

		String[] row;
		while (reader.readRecord()) {
			System.out.println("--------------------------");
			row = reader.getValues();
			System.out.println("Name: [" + row[0] + "]\nAddress: [" + row[1] + "]\nEmail: [" + row[2] + "]");
		}

		long endTime = new Date().getTime();
		System.out.println(endTime - beginTime);
	}
}
