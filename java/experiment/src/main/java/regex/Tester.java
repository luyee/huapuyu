package regex;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class Tester {

	@Test
	public void test() throws IOException {
		String regex = "^\\s?$|^(\\-[0-9]+(\\.[0-9]{1,2})?)$|^0+(\\.0{1,2})?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher("-100");
		System.out.println(matcher.matches() + "\t-100");
		System.out.println(Pattern.matches(regex, "0") + "\t0");
		System.out.println(Pattern.matches(regex, "00") + "\t00");
		System.out.println(Pattern.matches(regex, "-120.000") + "\t-120.000");
		System.out.println(Pattern.matches(regex, "-00.00") + "\t-00.00");
		System.out.println(Pattern.matches(regex, "-120.00") + "\t-120.00");
		System.out.println(Pattern.matches(regex, ".00") + "\t.00");
		System.out.println(Pattern.matches(regex, "-.01") + "\t-.01");
		System.out.println(Pattern.matches(regex, "100.00") + "\t100.00");
	}
}
