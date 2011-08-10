package com.baidu.rigel.unique.utils;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baidu.rigel.unique.utils.BusiUtils;
import com.baidu.rigel.unique.utils.Constant;
import com.baidu.rigel.unique.utils.SourceType;

/**
 * 单元测试BusiUtils类
 * 
 * @author Anders Zhu
 */
public class BusiUtilsTest {

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
	public void testGetCustName() {
		Assert.assertEquals("张飞", BusiUtils.getCustName("张飞", "赵云", "关羽"));
		Assert.assertEquals("赵云", BusiUtils.getCustName(null, "赵云", "关羽"));
		Assert.assertEquals("赵云", BusiUtils.getCustName(StringUtils.EMPTY, "赵云", "关羽"));
		Assert.assertEquals(StringUtils.EMPTY, BusiUtils.getCustName(null, null, null));
		Assert.assertEquals(StringUtils.EMPTY, BusiUtils.getCustName(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY));
	}

	@Test
	public void testGetIdNameSourceMap() {
		Assert.assertEquals(SourceType.CUST_SOURCE_BLACKLIST.toString(), BusiUtils.getIdNameSourceMap(1L, "张飞", SourceType.CUST_SOURCE_BLACKLIST.toString()).get(Constant.CUST_SOURCE));
		Assert.assertEquals(1L, BusiUtils.getIdNameSourceMap(1L, "张飞", SourceType.CUST_SOURCE_BLACKLIST.toString()).get(Constant.CUST_ID));
		Assert.assertEquals("张飞", BusiUtils.getIdNameSourceMap(1L, "张飞", SourceType.CUST_SOURCE_BLACKLIST.toString()).get(Constant.CUST_NAME));
	}

	@Test
	public void testGetIdNameSourceMapFromBlacklist() {
		Assert.assertEquals(SourceType.CUST_SOURCE_BLACKLIST.toString(), BusiUtils.getIdNameSourceMapFromBlacklist(1L, "张飞").get(Constant.CUST_SOURCE));
		Assert.assertEquals(1L, BusiUtils.getIdNameSourceMapFromBlacklist(1L, "张飞").get(Constant.CUST_ID));
		Assert.assertEquals("张飞", BusiUtils.getIdNameSourceMapFromBlacklist(1L, "张飞").get(Constant.CUST_NAME));
	}

	@Test
	public void testGetIdNameSourceMapFromShifen() {
		Assert.assertEquals(SourceType.CUST_SOURCE_SHIFEN.toString(), BusiUtils.getIdNameSourceMapFromShifen(1L, "张飞").get(Constant.CUST_SOURCE));
		Assert.assertEquals(1L, BusiUtils.getIdNameSourceMapFromShifen(1L, "张飞").get(Constant.CUST_ID));
		Assert.assertEquals("张飞", BusiUtils.getIdNameSourceMapFromShifen(1L, "张飞").get(Constant.CUST_NAME));
	}

	@Test
	public void testGetIdNameSourceMapFromSale() {
		Assert.assertEquals(SourceType.CUST_SOURCE_SALE.toString(), BusiUtils.getIdNameSourceMapFromSale(1L, "张飞").get(Constant.CUST_SOURCE));
		Assert.assertEquals(1L, BusiUtils.getIdNameSourceMapFromSale(1L, "张飞").get(Constant.CUST_ID));
		Assert.assertEquals("张飞", BusiUtils.getIdNameSourceMapFromSale(1L, "张飞").get(Constant.CUST_NAME));
	}
}
