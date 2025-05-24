package com.nafis.task_manager.cache;

import java.util.LinkedHashMap;
import java.util.Map;

class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public LRUCache(int capacity) {
        /*
         * In order to avoid expensive resizing
         *  -> Adding 1 with the required capacity
         *  -> Keeping the load factor 1.0f
         */
        super(capacity + 1, 1.0f, true);
        this.capacity = capacity + 1;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
