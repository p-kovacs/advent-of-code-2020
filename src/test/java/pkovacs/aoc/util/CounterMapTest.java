package pkovacs.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CounterMapTest {

    @Test
    public void test() {
        var map = new CounterMap<String>();
        assertEquals(0, map.size());
        assertEquals(0L, map.get("hello").longValue());
        assertEquals(0L, map.get("OK").longValue());

        map.put("hello", 42L);
        assertEquals(42L, map.get("hello").longValue());
        assertEquals(0L, map.get("OK").longValue());

        map.dec("hello");
        map.dec("hello");
        map.inc("OK");
        assertEquals(40L, map.get("hello").longValue());
        assertEquals(1L, map.get("OK").longValue());

        map.add("OK", 5);
        map.add("", -12);
        assertEquals(3, map.size());
        assertEquals(40L + 1 + 5 - 12, map.sum());
        assertEquals(1, map.count(6));
        map.add("", 18);
        assertEquals(2, map.count(6));

        map.add("extra", 0L);
        assertEquals(4, map.size());
    }

}