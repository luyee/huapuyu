package 数字;

import java.math.BigDecimal;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Tester2 {

	public static void main(String[] args) {
		BigDecimal d1 = new BigDecimal(1.0);
		BigDecimal d2 = new BigDecimal(2.2);

		System.out.println(d2);
		System.out.println(d1.max(d2));

		Integer[] ints = new Integer[] { null, null };
		System.out.println(NumberUtils.max(ArrayUtils.toPrimitive(ints)));
	}
}
