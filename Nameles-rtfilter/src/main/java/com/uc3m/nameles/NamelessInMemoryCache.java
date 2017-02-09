package com.uc3m.nameles;
 
import java.util.ArrayList;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;

public class NamelessInMemoryCache<K, T> {
 
    private long timeToLive;
    private LRUMap NamelessCacheMap;
 
    protected class CrunchifyCacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public T value;
 
        protected CrunchifyCacheObject(T value) {
            this.value = value;
        }
    }
 
    public NamelessInMemoryCache(long NamelessTimeToLive, final long NamelessTimerInterval, int maxItems) {
        this.timeToLive = NamelessTimeToLive * 1000;
 
        NamelessCacheMap = new LRUMap(maxItems);
 
        if (timeToLive > 0 && NamelessTimerInterval > 0) {
 
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(NamelessTimerInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });
 
            t.setDaemon(true);
            t.start();
        }
    }
 
    public void put(K key, T value) {
        synchronized (NamelessCacheMap) {
            NamelessCacheMap.put(key, new CrunchifyCacheObject(value));
        }
    }
 
    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (NamelessCacheMap) {
            CrunchifyCacheObject c = (CrunchifyCacheObject) NamelessCacheMap.get(key);
 
            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }
 
    public void remove(K key) {
        synchronized (NamelessCacheMap) {
            NamelessCacheMap.remove(key);
        }
    }
 
    public int size() {
        synchronized (NamelessCacheMap) {
            return NamelessCacheMap.size();
        }
    }
 
    @SuppressWarnings("unchecked")
    public void cleanup() {
 
        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;
 
        synchronized (NamelessCacheMap) {
            MapIterator itr = NamelessCacheMap.mapIterator();
 
            deleteKey = new ArrayList<K>((NamelessCacheMap.size() / 2) + 1);
            K key = null;
            CrunchifyCacheObject c = null;
 
            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (CrunchifyCacheObject) itr.getValue();
 
                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }
 
        for (K key : deleteKey) {
            synchronized (NamelessCacheMap) {
                NamelessCacheMap.remove(key);
            }
 
            Thread.yield();
        }
    }
}
