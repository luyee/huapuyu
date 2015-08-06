package com.anders.experiment.cache.imcache;

import java.util.concurrent.atomic.AtomicLong;

import com.cetsoft.imcache.cache.CacheLoader;
import com.cetsoft.imcache.cache.EvictionListener;
import com.cetsoft.imcache.cache.offheap.OffHeapCache;
import com.cetsoft.imcache.cache.offheap.bytebuffer.OffHeapByteBufferStore;
import com.cetsoft.imcache.cache.search.IndexHandler;
import com.cetsoft.imcache.serialization.Serializer;

public class MyOffHeapCache extends OffHeapCache<String, String> {

	public MyOffHeapCache(CacheLoader<String, String> cacheLoader, EvictionListener<String, String> evictionListener,
			IndexHandler<String, String> indexHandler, OffHeapByteBufferStore byteBufferStore,
			Serializer<String> serializer, long bufferCleanerPeriod, float bufferCleanerThreshold,
			int concurrencyLevel, long evictionPeriod) {
		super(cacheLoader, evictionListener, indexHandler, byteBufferStore, serializer, bufferCleanerPeriod,
				bufferCleanerThreshold, concurrencyLevel, evictionPeriod);
	}

	public AtomicLong getHit() {
		return hit;
	}

}
