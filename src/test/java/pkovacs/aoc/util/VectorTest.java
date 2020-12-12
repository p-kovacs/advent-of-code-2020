package pkovacs.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {

    @Test
    public void test() {
        var a = new Vector(0, 0);
        var b = new Vector(42, 12);

        a.add(b);
        assertEquals(b, a);

        a.add(-2, -2);
        assertEquals(new Vector(40, 10), a);
        a.neg();
        assertEquals(new Vector(-40, -10), a);
        a.set(40, 10);
        assertEquals(new Vector(40, 10), a);

        a.rotateRight();
        assertEquals(new Vector(10, -40), a);
        a.rotateRight();
        assertEquals(new Vector(-40, -10), a);
        a.rotateRight();
        assertEquals(new Vector(-10, 40), a);
        a.rotateRight();
        assertEquals(new Vector(40, 10), a);

        a.rotateLeft();
        assertEquals(new Vector(-10, 40), a);
        a.rotateLeft();
        assertEquals(new Vector(-40, -10), a);
        a.rotateLeft();
        assertEquals(new Vector(10, -40), a);
        a.rotateLeft();
        assertEquals(new Vector(40, 10), a);

        var c = new Vector(42, 12);
        var d = new Vector(c);
        assertEquals(0, Vector.dist(c, d));
        d.rotateRight();
        assertEquals(42 + 12 + 30, Vector.dist(c, d));
        c.rotateLeft();
        assertEquals(c.length() + d.length(), Vector.dist(c, d));
        c.neg();
        assertEquals(0, Vector.dist(c, d));
    }

}
