package xfire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TestImpl implements ITest
{
	public String example(String message)
	{
		return "hello " + message;
	}

	@Override
	public User example1(User model)
	{
		System.out.println(model.getName() + " : " + model.getAge());
		model.setName("zhuzhen");
		model.setAge(28);
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List example2(List l)
	{
		for (int i = 0; i < l.size(); i++)
		{
			System.out.println((String) l.get(i));
		}

		List al = new ArrayList();
		User u = new User();
		u.setName("hello world");
		u.setAge(90);
		al.add(u);

		return al;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map example3(Map m)
	{
		for (Iterator iterator = m.keySet().iterator(); iterator.hasNext();)
		{
			Integer key = (Integer) iterator.next();
			System.out.println(m.get(key));

		}

		User u = new User();
		u.setName("weishenme");
		u.setAge(99);

		Map map = new HashMap();
		map.put(1, u);

		return map;
	}
}