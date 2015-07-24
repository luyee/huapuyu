package com.anders.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerAdapter;

public class YoungCacheEventListener extends CacheEventListenerAdapter {

	public static final CacheEventListener INSTANCE = new YoungCacheEventListener();

	private Cache oldCache;

	public Cache getOldCache() {
		return oldCache;
	}

	public void setOldCache(Cache oldCache) {
		this.oldCache = oldCache;
	}

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
		// System.out.println(cache);
		System.out.println("young notifyElementRemoved" + element);
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
		// System.out.println(cache);
		System.out.println("young notifyElementPut" + element);
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
		// System.out.println(cache);
		System.out.println("young notifyElementUpdated" + element);
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		// System.out.println(cache);
		System.out.println("young notifyElementExpired" + element);
		oldCache.put(element);
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		// System.out.println(cache);
		System.out.println("young notifyElementEvicted" + element);
	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
		System.out.println("young notifyRemoveAll" + cache);
	}
}
