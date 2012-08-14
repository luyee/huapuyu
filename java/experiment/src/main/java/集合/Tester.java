package 集合;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Tester {
	public static void main(String[] args) {
		List<String> l = new ArrayList<String>();
		l.add("zhuzhen");
		l.add("guolili");
		l.add("guolili");

		for (String lTemp : l) {
			System.out.println(lTemp);
		}

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
		// at java.util.AbstractList$Itr.checkForComodification(AbstractList.java:372)
		// at java.util.AbstractList$Itr.next(AbstractList.java:343)
		// at 集合.Tester.main(Tester.java:48)
		// for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		MyVO myVO = new MyVO();
		List<String> list2222 = myVO.getList();
		list2222.clear();
		System.out.println("清空list是否影响MyVO中的list");
		for (Iterator<String> it = myVO.getList().iterator(); it.hasNext();) {
			System.out.println(it.next());
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
