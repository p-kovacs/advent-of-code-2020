package pkovacs.aoc.util;

import java.util.HashMap;

/**
 * Implements a long-valued map with implicit default value zero and convenience methods.
 * <p>
 * The {@code get} method is overridden to return zero instead of an absent value. Additional methods
 * are also provided for increasing/decreasing the stored value.
 */
public class CounterMap<K> extends HashMap<K, Long> {

    public CounterMap() {
        super();
    }

    public CounterMap(int initialCapacity) {
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
    public long inc(K key) {
        return add(key, 1);
    }

    /**
     * Decrements by one the value currently associated with {@code key}, and returns the new value.
     */
    public long dec(K key) {
        return add(key, -1);
    }

    /**
     * Adds {@code delta} to the value currently associated with {@code key}, and returns the new value.
     */
    public long add(K key, long delta) {
        long newValue = get(key) + delta;
        put(key, newValue);
        return newValue;
    }

    /**
     * Returns the sum of all values in this map.
     */
    public long sum() {
        return values().stream().mapToLong(v -> v).sum();
    }

    /**
     * Returns the count of the given value among all values in this map.
     */
    public long count(long value) {
        return values().stream().mapToLong(v -> v).filter(v -> v == value).count();
    }

}