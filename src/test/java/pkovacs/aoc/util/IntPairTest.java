package pkovacs.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class IntPairTest {

    @Test
    public void testIntPair() {
        var p = new IntPair(123, 42);
        var q = new IntPair(42, 123);
        var r = new IntPair(42, 123);

        assertNotEquals(p, q);
        assertEquals(q, r);

        assertEquals(42, p.b());
        assertEquals(p.a(), q.b());
        assertEquals(p.b(), q.a());
    }

}
