package ¼¯ºÏ;

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

		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			String str = it.next();
			if (str.equals("zhangsan"))
				it.remove();
		}

		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}
}
