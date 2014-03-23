package com.anders.dp.结构模式.合成;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tester {
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
	public void test() {
		IFile rootFolder = new 文件夹();

		IFile folder1 = new 文件夹();
		rootFolder.add(folder1);

		IFile folder2 = new 文件夹();
		folder1.add(folder2);

		IFile folder3 = new 文件夹();
		rootFolder.add(folder3);

		文件 file = new 文件();
		rootFolder.add(file);

		rootFolder.显示文件信息(StringUtils.EMPTY);
	}
}