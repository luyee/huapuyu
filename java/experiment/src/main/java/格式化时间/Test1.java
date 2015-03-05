package 格式化时间;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// List<Long> list = bitToInt(10);
		// for (Long long1 : list) {
		// System.out.println(long1);
		// }

		// Map<Long, Object> coreWordRuleMap = new HashMap<Long, Object>();
		// // 转化位id 填充到map中
		// buildCoreWordMap(10L, coreWordRuleMap);
		//
		// for (Iterator<Long> iterator = coreWordRuleMap.keySet().iterator();
		// iterator.hasNext();) {
		// Long type = iterator.next();
		// System.out.println(type);
		// System.out.println(coreWordRuleMap.get(type));
		//
		// }
		Short shot = new Short((short) 1);
		if (shot.equals(1))
			System.out.println("1");

		if (shot.equals((short) 1))
			System.out.println("2");

		if (shot.shortValue() == 1)
			System.out.println("3");

		if (shot.shortValue() == (short) 1)
			System.out.println("4");

		User1 user1 = new User1();
		System.out.println(user1.getUser2().getName());
		User2 user2 = user1.getUser2();
		user2.setName("guolili");

		System.out.println(user1.getUser2().getName());

		Long beginTime = System.nanoTime();

		System.out.println((System.nanoTime() - beginTime) / 1000);
	}

	private static List<Long> bitToInt(int intValue) {
		List<Long> ret = new ArrayList<Long>();
		int i = 0;
		while (true) {
			if ((Double.valueOf(Math.pow(2, i)).intValue() | intValue) == intValue) {
				ret.add(Long.valueOf(i));
			} else if (Double.valueOf(Math.pow(2, i)).intValue() > intValue) {
				break;
			}
			i++;
		}
		return ret;
	}

	private static void buildCoreWordMap(Long coreWordRuleId,
			Map<Long, Object> coreWordRuleMap) {
		List<Long> coreWordRuleList = null;
		// 将位表示转化为数值表示
		if (coreWordRuleId != null) {
			coreWordRuleList = bitToInt(coreWordRuleId.intValue());
		}

		if (coreWordRuleList != null) {
			for (Long cwr : coreWordRuleList) {
				coreWordRuleMap.put(cwr, cwr);
			}
		}
	}

}

class User1 {
	private String pwd;

	private User2 user2;

	public User1() {
		user2 = new User2();
		user2.setName("zhuzhen");
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public User2 getUser2() {
		return user2;
	}

	public void setUser2(User2 user2) {
		this.user2 = user2;
	}
}

class User2 {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}