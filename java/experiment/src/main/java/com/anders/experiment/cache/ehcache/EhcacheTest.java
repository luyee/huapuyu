package com.anders.experiment.cache.ehcache;

import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

public class EhcacheTest {
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(20000);

		CacheManager manager = CacheManager.newInstance("src/main/resources/ehcache.xml");
		manager.addCache("testCache");
		Cache test = manager.getCache("testCache");
		test.put(new Element("key1", "value1"));

		System.out.println(test.get("key1"));

		test.put(new Element("key1", null));

		System.out.println(test.get("key1"));
		System.out.println(test.get("key1"));
		System.out.println(test.get("key1"));
		System.out.println(test.get("key1").getExpirationTime());
		System.out.println(test.get("key1").getTimeToIdle());
		System.out.println(test.get("key1").getTimeToLive());
		for (long i = 0; i < 50; i++) {
			test.put(new Element("keyq" + i, new User(i, "zhuzhen" + i, 33)));
		}
		System.out.println(test.getSize());

		// Thread.sleep(2000);
		//
		// System.out.println(test.get("key1"));
		//
		// Thread.sleep(2000);
		//
		// System.out.println(test.get("key1"));
		//
		// Thread.sleep(2000);
		//
		// System.out.println(test.get("key1"));
		System.out.println(test.getStatistics().getLocalHeapSize());
		System.out.println(test.getStatistics().getLocalHeapSizeInBytes() / 1024 / 1024);

		manager.shutdown();

		Thread.sleep(100000000);
	}

	public void test2() {
		CacheManager manager = CacheManager.newInstance("src/main/resources/ehcache.xml");
		manager.addCache("testCache");
		Cache test = manager.getCache("testCache");
		// test.put(new Element("key1", "value1"));

		System.out.println(test.get("key1"));

		// test.put(new Element("key1", "value2"));

		System.out.println(test.get("key1"));
		System.out.println(test.get("key1"));
		System.out.println(test.get("key1"));

		manager.shutdown();
	}

	@Test
	public void test3() throws InterruptedException {
		CacheManager manager = CacheManager.newInstance("src/main/resources/ehcache.xml");
		Cache young = manager.getCache("young");
		Cache old = manager.getCache("old");
		Cache hot = manager.getCache("hot");

		YoungCacheEventListener youngCacheEventListener = getYoungCacheEventListener(young);
		youngCacheEventListener.setOldCache(old);

		OldCacheEventListener oldCacheEventListener = getOldCacheEventListener(old);
		oldCacheEventListener.setHotCache(hot);

		HotCacheEventListener hotCacheEventListener = getHotCacheEventListener(hot);
		hotCacheEventListener.setOldCache(old);

		young.put(new Element("zhuzhen", new User(1L, "zhuzhen", 33)));
		System.out.println(young.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(young.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(young.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(young.get("zhuzhen"));

		System.out.println("young ending");

		System.out.println(old.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(old.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(old.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(old.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(old.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(old.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(old.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(old.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(old.get("zhuzhen"));
		Thread.sleep(1000);
		System.out.println(old.get("zhuzhen"));

		System.out.println("old ending");

		System.out.println(hot.get("zhuzhen"));
		Thread.sleep(2000);
		System.out.println(hot.get("zhuzhen"));
		Thread.sleep(2000);
		System.out.println(hot.get("zhuzhen"));
		Thread.sleep(2000);
		System.out.println(hot.get("zhuzhen"));
		Thread.sleep(2000);
		System.out.println(hot.get("zhuzhen"));
		Thread.sleep(2000);
		System.out.println(hot.get("zhuzhen"));
		Thread.sleep(2000);
		System.out.println(hot.get("zhuzhen"));
		Thread.sleep(2000);
		System.out.println(hot.get("zhuzhen"));
		Thread.sleep(2000);
		System.out.println(hot.get("zhuzhen"));
		Thread.sleep(2000);
		System.out.println(hot.get("zhuzhen"));

		System.out.println("hot ending");

		for (int i = 0; i < 1000; i++) {
			hot.put(new Element("zhuzhen" + i, new User(1L, "zhuzhen", 33)));
		}

		manager.shutdown();
	}

	@Test
	public void test4() throws InterruptedException {
		CacheManager manager = CacheManager.newInstance("src/main/resources/ehcache.xml");
		Cache young = manager.getCache("young");
		Cache old = manager.getCache("old");
		Cache hot = manager.getCache("hot");

		YoungCacheEventListener youngCacheEventListener = getYoungCacheEventListener(young);
		youngCacheEventListener.setOldCache(old);

		OldCacheEventListener oldCacheEventListener = getOldCacheEventListener(old);
		oldCacheEventListener.setHotCache(hot);

		HotCacheEventListener hotCacheEventListener = getHotCacheEventListener(hot);
		hotCacheEventListener.setOldCache(old);

		for (int i = 0; i < 3973; i++) {
			hot.put(new Element("zhuzhen" + i, new User(1L, "zhuzhen", 33)));
		}

		manager.shutdown();
	}

	private CacheEventListener getCacheEventListener(Cache cache) {
		Set<CacheEventListener> set = cache.getCacheEventNotificationService().getCacheEventListeners();
		if (CollectionUtils.isNotEmpty(set)) {
			for (CacheEventListener cacheEventListener : set) {
				return cacheEventListener;
			}
		}

		return null;
	}

	private YoungCacheEventListener getYoungCacheEventListener(Cache cache) {
		CacheEventListener listener = getCacheEventListener(cache);
		if (listener != null) {
			return (YoungCacheEventListener) listener;
		}

		return null;
	}

	private OldCacheEventListener getOldCacheEventListener(Cache cache) {
		CacheEventListener listener = getCacheEventListener(cache);
		if (listener != null) {
			return (OldCacheEventListener) listener;
		}

		return null;
	}

	private HotCacheEventListener getHotCacheEventListener(Cache cache) {
		CacheEventListener listener = getCacheEventListener(cache);
		if (listener != null) {
			return (HotCacheEventListener) listener;
		}

		return null;
	}
}
