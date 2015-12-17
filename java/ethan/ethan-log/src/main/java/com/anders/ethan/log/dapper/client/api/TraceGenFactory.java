package com.anders.ethan.log.dapper.client.api;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import com.anders.ethan.log.dapper.client.api.spi.ITraceFactory;
import com.anders.ethan.log.dapper.client.api.spi.TraceGenerator;

/**
 * @author chris.xue trace生成器简单工厂类
 */
public class TraceGenFactory {
	public static final int NO_INIT = 0;
	public static final int ONGOING_INIT = 1;
	public static final int INIT_SUCCESS = 2;
	public static final int NOT_FOUND_IMP = 3;
	public static final int INVALID_VERSION = 4;
	public static final int BIND_FAILED = 5;

	// API兼容版本
	public static final String[] COMPATIBLE_VERSION = new String[] { "0.1" };
	// 静态绑定实现类
	private static final String TRACE_GEN_IMPL_CLASS_NAME = "com/anders/ethan/log/client/api/StaticTraceGenBinder.class";
	// 没有找到实现类时使用的默认实现，什么也不做
	private static final DefaultFactory DEFAULT_FACTORY = new DefaultFactory();

	private static volatile int INIT_STATE = NO_INIT;

	private TraceGenFactory() {
	}

	/**
	 * 获取trace gen
	 * 
	 * @return
	 */
	public static TraceGenerator getTraceGen() {
		return getTraceFactory().getTraceGen();
	}

	private static ITraceFactory getTraceFactory() {
		if (INIT_STATE == NO_INIT || INIT_STATE == ONGOING_INIT) {
			synchronized (TraceGenFactory.class) {
				if (INIT_STATE == NO_INIT) {
					INIT_STATE = ONGOING_INIT;
					doInit();
				}
			}
		}
		switch (INIT_STATE) {
		case ONGOING_INIT:
			throw new RuntimeException("invalid state, state is "
					+ ONGOING_INIT);
		case NOT_FOUND_IMP:
			return DEFAULT_FACTORY;
		case INIT_SUCCESS:
			return StaticTraceGenBinder.SINGLETON().getTraceFactory();
		case INVALID_VERSION:
			throw new IllegalArgumentException(
					"incompatible version, required is "
							+ StaticTraceGenBinder.SINGLETON()
									.getRequiredVersion() + ", compatible is "
							+ Arrays.toString(COMPATIBLE_VERSION));
		case BIND_FAILED:
			throw new RuntimeException("bind failed");
		default:
			throw new RuntimeException("unexpected reached code");
		}
	}

	/**
	 * 初始化
	 */
	private static void doInit() {
		bind();
		if (INIT_STATE == INIT_SUCCESS) {
			versionCheck();
		}
		// PatrolUtils.registerAppConfigMBean(AppTraceConfig.getLocalConfig());
	}

	/**
	 * 绑定
	 * 
	 * @return
	 */
	private static void bind() {
		try {
			Set<URL> multiImplSet = findImpossibleTraceGenImpl();
			reportFindMultiImpl(multiImplSet);
			// bind
			StaticTraceGenBinder.SINGLETON();
			reportActualBinder(multiImplSet);
			INIT_STATE = INIT_SUCCESS;
		} catch (NoClassDefFoundError e) {
			System.err.println("no trace generator impl found");
			System.err.println("will use default trace generator");
			INIT_STATE = NOT_FOUND_IMP;
		} catch (Exception e) {
			System.err.println("bind failed");
			e.printStackTrace();
			INIT_STATE = BIND_FAILED;
		}
	}

	/**
	 * 版本检查
	 * 
	 * @return
	 */
	private static void versionCheck() {
		String version = StaticTraceGenBinder.SINGLETON().getRequiredVersion();
		for (String compVersion : COMPATIBLE_VERSION) {
			if (compVersion.startsWith(version)) {
				return;
			}
		}
		INIT_STATE = INVALID_VERSION;
	}

	private static void reportActualBinder(Set<URL> multiImplSet) {
		if (multiImplSet.size() > 1) {
			System.err.println("Actual Binder is ["
					+ StaticTraceGenBinder.SINGLETON().getTraceGenStr() + "]");
		}
	}

	private static void reportFindMultiImpl(Set<URL> multiImplSet) {
		if (multiImplSet.size() > 1) {
			System.err.println("find multiple StaticTraceGenBinder");
			for (URL url : multiImplSet) {
				System.err.println("find binder [" + url + "]");
			}
		}
	}

	/**
	 * 查找可用的实现
	 * 
	 * @return
	 */
	private static Set<URL> findImpossibleTraceGenImpl() {
		Set<URL> implSets = new LinkedHashSet<URL>(2, 0.5f);
		try {
			ClassLoader classLoader = TraceGenFactory.class.getClassLoader();
			Enumeration<URL> urlEnumeration;
			if (classLoader != null) {
				urlEnumeration = classLoader
						.getResources(TRACE_GEN_IMPL_CLASS_NAME);
			} else {
				urlEnumeration = ClassLoader
						.getSystemResources(TRACE_GEN_IMPL_CLASS_NAME);
			}
			while (urlEnumeration.hasMoreElements()) {
				URL url = urlEnumeration.nextElement();
				implSets.add(url);
			}
		} catch (IOException e) {
			System.err.println("find trace generater impl failed");
		}

		return implSets;
	}
}
