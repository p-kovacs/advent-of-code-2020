package pkovacs.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PairTest {

    @Test
    public void testGeneralPair() {
        var p = new Pair<>("hello", 42);
        var q = new Pair<>(42, "hello");
        var r = new Pair<>(42, "hello");

        assertNotEquals(p, q);
        assertEquals(q, r);

        assertEquals("hello", p.first);
        assertEquals(p.first, q.second);
        assertEquals(p.second, q.first);
    }

    @Test
    public void testIntPair() {
        var p = new IntPair(123, 42);
        var q = new IntPair(42, 123);
        var r = new IntPair(42, 123);

        assertNotEquals(p, q);
        assertEquals(q, r);

        assertEquals(42, p.b);
        assertEquals(p.a, q.b);
        assertEquals(p.b, q.a);
    }

    @Test
    public void testLongPair() {
        var p = new LongPair(123_456_789_000L, 42);
        var q = new LongPair(42, 123_456_789_000L);
        var r = new LongPair(42, 123_456_789_000L);

        assertNotEquals(p, q);
        assertEquals(q, r);

        assertEquals(123456789000L, p.a);
        assertEquals(p.a, q.b);
        assertEquals(p.b, q.a);
    }

}
