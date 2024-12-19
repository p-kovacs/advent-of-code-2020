package com.github.pkovacs.aoc.util;

import java.util.HashMap;
import java.util.stream.LongStream;

/**
 * A long-valued {@link HashMap} storing counters associated with keys. This class provides additional methods to
 * operate on the counter values conveniently, assuming the default initial value of each counter to be zero.
 * <p>
 * For example, if you call {@link #inc(Object)} on an empty map, it will associate {@code 1} with the given key.
 * However, all standard methods of {@link java.util.Map} work identically, so {@link #get(Object)} returns
 * {@code null} for a key that is not contained by the map explicitly. Use {@link #getValue(Object)} when you
 * would like to exploit the default value.
 */
public final class CounterMap<K> extends HashMap<K, Long> {

    public CounterMap() {
        super();
    }

    public CounterMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Returns the value associated with the given key or zero if the map does not contain the key.
     * This is equivalent to {@link #getOrDefault(Object, Long) getOrDefault(key, 0L)}.
     */
    public long getValue(K key) {
        return getOrDefault(key, 0L);
    }

    /**
     * Associates the given {@code int} value with the given key. This method is equivalent to the standard
     * {@link #put(Object, Long)} method, but it can be directly called with integer literals.
     */
    public long put(K key, int value) {
        put(key, Long.valueOf(value));
        return value;
    }

    /**
     * Increments the value associated with {@code key} by one, and returns the new value.
     * If the map did not contain the key, the old value is assumed to be zero.
     */
    public long inc(K key) {
        return add(key, 1);
    }

    /**
     * Decrements the value associated with {@code key} by one, and returns the new value.
     * If the map did not contain the key, the old value is assumed to be zero.
     */
    public long dec(K key) {
        return add(key, -1);
    }

    /**
     * Adds {@code delta} to the value associated with {@code key}, and returns the new value.
     * If the map did not contain the key, the old value is assumed to be zero.
     */
    public long add(K key, long delta) {
        long newValue = getValue(key) + delta;
        put(key, newValue);
        return newValue;
    }

    /**
     * Multiplies the value associated with {@code key} by {@code factor}, and returns the new value.
     * If the map did not contain the key, the old value is assumed to be zero.
     */
    public long multiply(K key, long factor) {
        long newValue = getValue(key) * factor;
        put(key, newValue);
        return newValue;
    }

    /**
     * Returns a {@link LongStream} view of the values contained in this map.
     */
    public LongStream valueStream() {
        return values().stream().mapToLong(Long::longValue);
    }

    /**
     * Returns the sum of the values contained in this map.
     */
    public long sum() {
        return valueStream().sum();
    }

    /**
     * Returns the minimum of the values contained in this map.
     *
     * @throws java.util.NoSuchElementException if the map is empty
     */
    public long min() {
        return valueStream().min().orElseThrow();
    }

    /**
     * Returns the maximum of the values contained in this map.
     *
     * @throws java.util.NoSuchElementException if the map is empty
     */
    public long max() {
        return valueStream().max().orElseThrow();
    }

    /**
     * Returns the count of the given value among all values contained in this map.
     */
    public long count(long value) {
        return valueStream().filter(v -> v == value).count();
    }

}
