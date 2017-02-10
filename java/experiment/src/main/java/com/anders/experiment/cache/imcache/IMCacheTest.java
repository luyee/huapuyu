package com.anders.experiment.cache.imcache;

import com.cetsoft.imcache.cache.Cache;
import com.cetsoft.imcache.cache.CacheLoader;
import com.cetsoft.imcache.cache.EvictionListener;
import com.cetsoft.imcache.cache.builder.CacheBuilder;
import com.cetsoft.imcache.cache.offheap.bytebuffer.OffHeapByteBufferStore;

public class IMCacheTest {
	public static void main(String[] args) throws InterruptedException {
		Cache<String, String> cache = CacheBuilder.heapCache().cacheLoader(new MyCacheLoader()).capacity(10000).build();
		// If there is not a user in the heap cache it'll be loaded via userDAO.
		String user = cache.get("fff");
		System.out.println(user);
		// User newUser = new User("email", "Richard", "Murray")
		// When maximum value for cache size is reached, eviction event occurs.
		// In case of eviction, newUser will be saved to db.
		cache.put("name", "zhuzhen");
		System.out.println(cache.get("name"));

		// OffHeapByteBufferStore bufferStore = new OffHeapByteBufferStore(8388608, 10);
		// final Cache<Integer, VersionedItem<String>> offHeapCache = CacheBuilder.versionedOffHeapCache()
		// .storage(bufferStore).build();
		// VersionedItem<String> versionedItem = offHeapCache.get(12);

		OffHeapByteBufferStore bufferStore = new OffHeapByteBufferStore(1048576, 1000);
		final Cache<String, String> offHeapCache = CacheBuilder.offHeapCache().evictionListener(new MyListener())
				.storage(bufferStore).evictionPeriod(1000).bufferCleanerPeriod(1000).build();

		// OffHeapCacheBuilder offHeapCacheBuilder = CacheBuilder.offHeapCache();

		// MyOffHeapCache myOffHeapCache = new MyOffHeapCache(CacheBuilder.offHeapCache(), new MyListener(),
		// indexHandler, bufferStore, serializer, bufferCleanerPeriod, bufferCleanerThreshold, concurrencyLevel,
		// evictionPeriod)

		// Thread.sleep(20000);

		for (int i = 0; i < 10000000; i++) {
			offHeapCache.put("name" + i, "helloworld888888888888888888888888888888888888888888888888");
			System.out.println(i);
		}
		// offHeapCache.put("name", "zhuzhen");
		// System.out.println(offHeapCache.get("name"));
		// System.out.println(offHeapCache.get("name"));
		// System.out.println(offHeapCache.get("name"));
		// offHeapCache.put("name", "guolili");
		// System.out.println(offHeapCache.get("name"));
		// System.out.println(offHeapCache.hitRatio());
		//
		//
		//
		// System.out.println(offHeapCache.get("name"));
		// System.out.println(offHeapCache.get("name"));
		// System.out.println(offHeapCache.get("name"));
		// offHeapCache.put("name", "guolili");
		System.out.println(offHeapCache.get("name"));
	}
}

class MyCacheLoader implements CacheLoader<String, String> {

	@Override
	public String load(String key) {
		return "zhuzhen";
	}

}

class MyListener implements EvictionListener {

	@Override
	public void onEviction(Object key, Object value) {
		System.out.println(key + ":" + value);
	}
}
