package 排序;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;

public class MyComparator<T> implements Comparator<T> {

	private Collator collator = Collator.getInstance();

	public MyComparator() {
	}

	@Override
	public int compare(Object o1, Object o2) {
		// 把字符串转换为一系列比特，它们可以以比特形式与 CollationKeys 相比较
		CollationKey key1 = collator.getCollationKey(o1.toString());// 要想不区分大小写进行比较用o1.toString().toLowerCase()
		CollationKey key2 = collator.getCollationKey(o2.toString());

		return key1.compareTo(key2);// 返回的分别为1,0,-1 分别代表大于，等于，小于。要想按照字母降序排序的话 加个“-”号
	}

}
