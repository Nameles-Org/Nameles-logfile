package com.uc3m.nameles;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class NamelessInMemoryCacheRef {
	NamelessInMemoryCache<String, List<Integer>> cacheRef;

	public NamelessInMemoryCacheRef(NamelessInMemoryCache<String, List<Integer>> cacheRef) {
		this.cacheRef = cacheRef;
	}

	public static NamelessInMemoryCache<String, List<Integer>> cache(String day) throws InterruptedException, IOException {
		ConcurrentHashMap<String, List<Integer>> FiltersHashMapRef = HashMapFilteringRef.FiltersHashMap("nmls16", day);

		NamelessInMemoryCache<String, List<Integer>> cache = new NamelessInMemoryCache<String, List<Integer>>(3600, 500,FiltersHashMapRef.size());
		NamelessInMemoryCacheRef namelessCacheRef = new NamelessInMemoryCacheRef(cache);
		namelessCacheRef.AddRemoveReferrer(FiltersHashMapRef);

		return cache;
	}

	private void AddRemoveReferrer(ConcurrentHashMap<String, List<Integer>> listCache) {

		for (Entry<String, List<Integer>> entry : listCache.entrySet()) {
			cacheRef.put(entry.getKey(), entry.getValue());
		}
		System.out.println("Cache Ref. size ----> " + cacheRef.size());


	}

}