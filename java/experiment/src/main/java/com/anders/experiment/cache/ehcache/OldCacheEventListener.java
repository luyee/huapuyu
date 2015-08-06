package com.anders.experiment.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerAdapter;

public class OldCacheEventListener extends CacheEventListenerAdapter {

	public static final CacheEventListener INSTANCE = new OldCacheEventListener();

	private Cache hotCache;

	public Cache getHotCache() {
		return hotCache;
	}

	public void setHotCache(Cache hotCache) {
		this.hotCache = hotCache;
	}

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
		// System.out.println(cache);
		System.out.println("old notifyElementRemoved" + element);
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
		// System.out.println(cache);
		System.out.println("old notifyElementPut" + element);
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
		// System.out.println(cache);
		System.out.println("old notifyElementUpdated" + element);
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		// System.out.println(cache);
		System.out.println("old notifyElementExpired" + element);
		hotCache.put(element);
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		// System.out.println(cache);
		System.out.println("old notifyElementEvicted" + element);
	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
		System.out.println("old notifyRemoveAll" + cache);
	}
}
