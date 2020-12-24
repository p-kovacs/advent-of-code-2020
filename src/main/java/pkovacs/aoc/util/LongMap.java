package pkovacs.aoc.util;

import java.util.HashMap;

/**
 * Implements a long-valued map with implicit default value zero.
 * <p>
 * The {@code get} method is overridden to return zero instead of an absent value. Additional methods
 * are also provided for increasing/decreasing the stored value.
 */
public class LongMap<K> extends HashMap<K, Long> {

    public LongMap() {
        super();
    }

    public LongMap(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public Long get(Object key) {
        return getOrDefault(key, 0L);
    }

    @Override
    public Long put(K key, Long value) {
        Long oldValue = get(key);
        super.put(key, value);
        return oldValue;
    }

    /**
     * Increments by one the value currently associated with {@code key}, and returns the new value.
     */
    public long incrementAndGet(K key) {
        return addAndGet(key, 1);
    }

    /**
     * Decrements by one the value currently associated with {@code key}, and returns the new value.
     */
    public long decrementAndGet(K key) {
        return addAndGet(key, -1);
    }

    /**
     * Adds {@code delta} to the value currently associated with {@code key}, and returns the new value.
     */
    public long addAndGet(K key, long delta) {
        long newValue = get(key) + delta;
        put(key, newValue);
        return newValue;
    }

    /**
     * Returns the sum of all values in this map.
     */
    public long sum() {
        return values().stream().mapToLong(Long::longValue).sum();
    }

    /**
     * Returns the count of the given value among all values in this map.
     */
    public long count(long value) {
        return values().stream().mapToLong(Long::longValue).filter(v -> v == value).count();
    }

}
