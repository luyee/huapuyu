package com.anders.ssh.common;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-common-test.xml" })
public class TwoLevelMapConfigTest {
	@Resource
	private TwoLevelMapConfig twoLevelMapConfig;

	@Test
	public void test() {
		Map<String, Map<String, String>> map = twoLevelMapConfig.getTwoLevelMap();
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			Map<String, String> nestedMap = map.get(key);
			for (Iterator<String> iterator = nestedMap.keySet().iterator(); iterator.hasNext();) {
				String nestedKey = iterator.next();
				System.out.println(nestedMap.get(nestedKey));
			}
		}
	}

	@Test
	public void test1() {
		Map<String, Map<String, User>> map = twoLevelMapConfig.getTwoLevelUserMap();
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			Map<String, User> nestedMap = map.get(key);
			for (Iterator<String> iterator = nestedMap.keySet().iterator(); iterator.hasNext();) {
				String nestedKey = iterator.next();
				System.out.println(nestedMap.get(nestedKey).getName());
			}
		}
	}

	@Test
	public void test2() {
		Map<String, UserMap> map = twoLevelMapConfig.getTwoLevelUser2Map();
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			UserMap userMap = map.get(key);
			System.out.println(userMap.getName());
			for (Iterator<String> iterator = userMap.getMap().keySet().iterator(); iterator.hasNext();) {
				String nestedKey = iterator.next();
				System.out.println(userMap.getMap().get(nestedKey).getName());
			}
		}
	}
}