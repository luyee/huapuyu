package csv;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class OpenCsvTest {

	@Test
	public void test() throws IOException {
		long beginTime = new Date().getTime();
		String path = getClass().getResource("OpenCsv.csv").getPath();
		System.out.println(path);
		CSVReader reader1 = new CSVReader(new FileReader(path));
		String[] row;
		while ((row = reader1.readNext()) != null) {
			System.out.println("--------------------------");
			System.out.println("Name: [" + row[0] + "]\nAddress: [" + row[1] + "]\nEmail: [" + row[2] + "]");
		}

		// Try writing it back out as CSV to the console
		// CSVReader reader2 = new CSVReader(new FileReader(path));
		// List<String[]> allElements = reader2.readAll();
		// StringWriter sw = new StringWriter();
		// CSVWriter writer = new CSVWriter(sw);
		// writer.writeAll(allElements);
		//
		// System.out.println("\n\nGenerated CSV File:\n\n");
		// System.out.println(sw.toString());

		long endTime = new Date().getTime();
		System.out.println(endTime - beginTime);
	}

}
