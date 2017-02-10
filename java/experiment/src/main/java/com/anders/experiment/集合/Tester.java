package com.anders.experiment.集合;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Tester {
	public static void main(String[] args) {
		List<String> l = new ArrayList<String>();
		l.add("zhuzhen");
		l.add("guolili");
		l.add("guolili");
		l.add("3");
		l.add("1");
		l.add("2");

		for (String lTemp : l) {
			System.out.println(lTemp);
		}

		System.out.println("移动位置");

		l.add(l.remove(0));

		for (String lTemp : l) {
			System.out.println(lTemp);
		}

		System.out.println("移动位置结束");

		Set<String> s = new HashSet<String>();
		s.add("zhuzhen");
		s.add("guolili");
		s.add("guolili");

		for (String sTemp : s) {
			System.out.println(sTemp);
		}

		System.out.println("-----------------------------");

		List<String> list = new ArrayList<String>();
		list.add("zhangsan");
		list.add("zhuzhen");
		list.add("guolili");
		list.add("cat");

		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			String str = it.next();
			if (str.equals("zhangsan"))
				it.remove();
		}

		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}

		// 下面两种方法抛出异常
		// Exception in thread "main" java.util.ConcurrentModificationException
		// at
		// java.util.AbstractList$Itr.checkForComodification(AbstractList.java:372)
		// at java.util.AbstractList$Itr.next(AbstractList.java:343)
		// at 集合.Tester.main(Tester.java:48)
		// for (Iterator<String> iterator = list.iterator();
		// iterator.hasNext();) {
		// String string = iterator.next();
		// if (string.equals("cat")) {
		// list.add("cat5555");
		// // list.remove("cat");add和remove都会产生异常
		// }
		// }
		try {
			for (String s123 : list) {
				if (s123.equals("cat")) {
					list.add("cat5555");
					// list.remove("cat");add和remove都会产生异常
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		MyVO myVO = new MyVO();
		List<String> list2222 = myVO.getList();
		list2222.clear();
		System.out.println("清空list是否影响MyVO中的list");
		for (Iterator<String> it = myVO.getList().iterator(); it.hasNext();) {
			System.out.println(it.next());
		}

		System.out.println("************************************************");

		// 记录插入顺序
		LinkedHashMap<Integer, String> lhm = new LinkedHashMap<Integer, String>();
		lhm.put(1, "zhuzhen");
		lhm.put(3, "zhurongbao");
		lhm.put(4, "huangxiaoyan");
		lhm.put(2, "guolili");

		for (Iterator<Integer> it = lhm.keySet().iterator(); it.hasNext();) {
			System.out.print(it.next().toString() + " ");
		}

		System.out.println("");

		// 不记录插入顺序
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(1, "zhuzhen");
		hm.put(3, "zhurongbao");
		hm.put(4, "huangxiaoyan");
		hm.put(2, "guolili");

		for (Iterator<Integer> it = hm.keySet().iterator(); it.hasNext();) {
			System.out.print(it.next().toString() + " ");
		}

		System.out.println("");

		Set<String> treeSet = new TreeSet<String>();
		treeSet.add("ds1");
		treeSet.add("ds0");
		treeSet.add("ds1");

		System.out.println("TreeSet测试");
		for (String dbname : treeSet) {
			System.out.println(dbname);
		}

		Set<String> linkedHashSet = new LinkedHashSet<String>();
		linkedHashSet.add("ds1");
		linkedHashSet.add("ds0");
		linkedHashSet.add("ds1");

		System.out.println("LinkedHashSet测试");
		for (String dbname : linkedHashSet) {
			System.out.println(dbname);
		}
	}
}

class MyVO {
	private List<String> list;

	public MyVO() {
		list = new ArrayList<String>();
		list.add("zhangsan");
		list.add("zhuzhen");
		list.add("guolili");
		list.add("cat");
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

}
