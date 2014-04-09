package com.anders.experiment.lucene;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.Directory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LuceneTest {

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
	public void test内存基本操作() throws IOException, ParseException {
		Directory directory = LuceneUtil.index内存();
		LuceneUtil.search内存(directory);
	}

	@Test
	public void test文件基本操作() throws IOException, ParseException {
		LuceneUtil.清空索引文件();
		LuceneUtil.index文件();
		LuceneUtil.search文件();
	}

	@Test
	public void test文件_自定义内容() throws IOException, ParseException {
		LuceneUtil.index文件_自定义内容();
		LuceneUtil.search文件_自定义内容();
	}
}
