package cxf;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

@WebService(endpointInterface = "cxf.ITest")
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

	@Override
	public List<User> example2(List<String> l)
	{
		for (int i = 0; i < l.size(); i++)
		{
			System.out.println((String) l.get(i));
		}

		List<User> al = new ArrayList<User>();
		User u = new User();
		u.setName("hello world");
		u.setAge(90);
		al.add(u);

		return al;
	}

	// 注意：如何在CXF中传递Map，目前还没有什么好方法，网上的例子是通过写转换函数，不知道有没有简单的方法

	// @Override
	// public Map<Integer, User> example3(Map<Integer, String> m)
	// {
	// for (Iterator<Integer> iterator = m.keySet().iterator();
	// iterator.hasNext();)
	// {
	// Integer key = iterator.next();
	// System.out.println(m.get(key));
	//
	// }
	//
	// User u = new User();
	// u.setName("weishenme");
	// u.setAge(99);
	//
	// Map<Integer, User> map = new HashMap<Integer, User>();
	// map.put(1, u);
	//
	// return map;
	// }
}