package asm;

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
		// ClassReader cr = new ClassReader(className);// className为原始类名，形如com.xyz.Abc，但在web容器环境下不可这样写
		// ClassWriter cw = new ClassWriter(false);
		// AsmClassVisit mv = new AsmClassVisit(cw);
		// cr.accept(mv, true);
		// MyspringClassLoader mcl = new MyspringClassLoader(this.getClass().getClassLoader());// 定义自己的classLoader，用于根据byte数组load类
		// Class newClass = mcl.getClass(cw.toByteArray(), null);// 生成子类
	}

}
