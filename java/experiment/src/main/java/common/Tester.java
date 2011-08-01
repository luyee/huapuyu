package common;

import org.apache.commons.lang.StringUtils;

public class Tester {
	public static void main(String[] args) {
		String s1 = null;
		String s2 = " ";
		String s3 = StringUtils.EMPTY;

		if (StringUtils.isBlank(s1))
			System.out.println("null");

		if (StringUtils.isBlank(s2))
			System.out.println("space");

		if (StringUtils.isBlank(s3))
			System.out.println("empty");

		System.out.println("------------------------");

		if (StringUtils.isEmpty(s1))
			System.out.println("null");

		if (StringUtils.isEmpty(s2))
			System.out.println("space");

		if (StringUtils.isEmpty(s3))
			System.out.println("empty");
	}
}
