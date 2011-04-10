package ¼¯ºÏ;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tester
{
	public static void main(String[] args)
	{
		List<String> l = new ArrayList<String>();
		l.add("zhuzhen");
		l.add("guolili");
		l.add("guolili");

		for (String lTemp : l)
		{
			System.out.println(lTemp);
		}

		Set<String> s = new HashSet<String>();
		s.add("zhuzhen");
		s.add("guolili");
		s.add("guolili");

		for (String sTemp : s)
		{
			System.out.println(sTemp);
		}
	}
}
