package com.baidu.rigel.unique.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 单元测试Utils类
 * 
 * @author Anders Zhu
 */
public class UtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLimitList() {
		// 创建模拟数据
		List<Map<String, Object>> srcList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("name", "张飞");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("name", "赵云");
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("name", "关羽");
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("name", "黄忠");
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("name", "马超");
		srcList.add(map1);
		srcList.add(map2);
		srcList.add(map3);
		srcList.add(map4);
		srcList.add(map5);
		// 测试
		Assert.assertEquals(4, Utils.limitList(srcList, 4).size());
		Assert.assertEquals(0, Utils.limitList(srcList, 0).size());
		Assert.assertEquals(5, Utils.limitList(srcList, 9).size());
		Assert.assertEquals(0, Utils.limitList(new ArrayList<Map<String, Object>>(), 4).size());
		Assert.assertEquals(0, Utils.limitList(null, 4).size());
		Assert.assertEquals(0, Utils.limitList(srcList, -1).size());
	}

	@Test
	public void testGetIsNotEmptyFirstParam() {
		Assert.assertEquals("张飞", Utils.getFirstNotEmptyParam("张飞", "赵云", "关羽", "黄总", "马超"));
		Assert.assertEquals("赵云", Utils.getFirstNotEmptyParam(null, "赵云", "关羽", "黄总", "马超"));
		Assert.assertEquals("赵云", Utils.getFirstNotEmptyParam(StringUtils.EMPTY, "赵云", "关羽", "黄总", "马超"));
		Assert.assertEquals("黄总", Utils.getFirstNotEmptyParam(null, null, null, "黄总", "马超"));
		Assert.assertEquals("黄总", Utils.getFirstNotEmptyParam(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, "黄总", "马超"));
		Assert.assertEquals(StringUtils.EMPTY, Utils.getFirstNotEmptyParam(null, null, null, null, null));
		Assert.assertEquals(StringUtils.EMPTY, Utils.getFirstNotEmptyParam(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY));
	}
}
