package com.anders.imcache;

import com.cetsoft.imcache.cache.SearchableCache;
import com.cetsoft.imcache.cache.builder.OffHeapCacheBuilder;

public class MyOffHeapCacheBuilder extends OffHeapCacheBuilder {
	@Override
	public <K, V> SearchableCache<K, V> build() {
		// if (super.byteBufferStore == null) {
		// throw new NecessaryArgumentException("ByteBufferStore must be set!");
		// }
		// return new OffHeapCache<K, V>((CacheLoader<K, V>) cacheLoader, (EvictionListener<K, V>) evictionListener,
		// (IndexHandler<K, V>) indexHandler, byteBufferStore, (Serializer<V>) serializer, bufferCleanerPeriod,
		// bufferCleanerThreshold, concurrencyLevel, evictionPeriod);
		return null;
	}
}
