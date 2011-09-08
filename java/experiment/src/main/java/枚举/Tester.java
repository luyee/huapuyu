package 枚举;

import java.util.HashMap;
import java.util.Map;

public class Tester {
	public static void main(String[] args) throws Exception {
		Type t1 = Type.可用;
		Type t2 = Type.不可用;
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t1.getValue());
		System.out.println(t2.getValue());
		System.out.println(Type.可用.getValue());
		System.out.println(Type.不可用.getValue());

		System.out.println("----------------------");

		for (Period period : Period.values()) {
			System.out.println(period.name());
			System.out.println(period.ordinal());
			System.out.println(period.getLabel());
			System.out.println(period.getValue());
			System.out.println(Period.LONG);
			System.out.println(Period.SHORT);
			System.out.println("----------------------");
		}

		Map<String, String> map = new HashMap<String, String>();
		// map.put("123", SourceType.CUST_SOURCE_BLACKLIST);
	}
}

enum SourceType {
	/**
	 * 销售
	 */
	CUST_SOURCE_SALE {
		@Override
		public String toString() {
			return "custSourceSale";
		}
	},
	/**
	 * 十分
	 */
	CUST_SOURCE_SHIFEN {
		@Override
		public String toString() {
			return "custSourceShifen";
		}
	},
	/**
	 * 黑名单
	 */
	CUST_SOURCE_BLACKLIST {
		@Override
		public String toString() {
			return "custSourceBlacklist";
		}
	}
}
