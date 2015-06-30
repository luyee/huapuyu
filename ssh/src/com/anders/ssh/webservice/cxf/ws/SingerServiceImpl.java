package com.anders.ssh.webservice.cxf.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

@WebService(endpointInterface = "com.anders.ssh.webservice.cxf.ws.SingerService")
public class SingerServiceImpl implements SingerService {
	public String hello(String name) {
		System.out.println("hello: " + name);
		return "hello: " + name;
	}

	@Override
	public Singer getSinger(Singer singer) {
		System.out.println(singer);
		singer.setName("zhuzhen");
		singer.setAge(28);
		return singer;
	}

	@Override
	public List<Singer> getAllSinger(List<String> list) {
		for (String s : list)
			System.out.println(s);

		List<Singer> singerList = new ArrayList<Singer>();
		singerList.add(new Singer("zhuzhen", 29));
		singerList.add(new Singer("guolili", 26));
		return singerList;
	}

	// 娉ㄦ剰锛氬浣曞湪CXF涓紶閫扢ap锛岀洰鍓嶈繕娌℃湁浠�涔堝ソ鏂规硶锛岀綉涓婄殑渚嬪瓙鏄�氳繃鍐欒浆鎹㈠嚱鏁帮紝涓嶇煡閬撴湁娌℃湁绠�鍗曠殑鏂规硶

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