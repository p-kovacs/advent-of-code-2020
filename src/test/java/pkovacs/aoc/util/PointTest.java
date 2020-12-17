package pkovacs.aoc.util;

import java.util.HashSet;
import java.util.Set;

import com.google.common.math.LongMath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PointTest {

    @Test
    public void test() {
        var a = new Point(12, 42, 0);
        var b = new Point(12, 42);
        var array = new int[] { 12, 42 };
        var c = new Point(array);

        assertEquals(12, a.get(0));
        assertEquals(42, a.get(1));
        assertEquals(0, a.get(2));

        assertEquals(3, a.dim());
        assertEquals(2, b.dim());
        assertNotEquals(a, b);
        assertEquals(b, c);

        assertEquals(54, a.length());
        assertEquals(54, b.length());
        assertEquals(57, Point.dist(a, new Point(-1,-1,-1)));

        assertEquals(new Point(24, 84), Point.add(b, c));
        assertEquals(new Point(0, 0), Point.sub(b, c));
        assertEquals(b, Point.sub(Point.add(b, b), c));

        assertEquals(8, b.getNeighbors().size());
        assertEquals(8, b.getNeighbors().stream().distinct().count());
        assertEquals(Set.of(new Point(11, 41), new Point(11, 42), new Point(11, 43),
                new Point(12, 41), new Point(12, 43),
                new Point(13, 41), new Point(13, 42), new Point(13, 43)),
                new HashSet<>(b.getNeighbors()));

        assertEquals(9, b.getNeighbors(true).size());
        assertEquals(9, b.getNeighbors(true).stream().distinct().count());
        assertEquals(Set.of(new Point(11, 41), new Point(11, 42), new Point(11, 43),
                new Point(12, 41), new Point(12, 42), new Point(12, 43),
                new Point(13, 41), new Point(13, 42), new Point(13, 43)),
                new HashSet<>(b.getNeighbors(true)));

        for (int dim = 1; dim <= 10; dim++) {
            var point = new Point(new int[dim]);
            long expected = LongMath.pow(3, dim);
            assertEquals(expected - 1, point.getNeighbors(false).size());
            assertEquals(expected - 1, point.getNeighbors(false).stream().distinct().count());
            assertEquals(expected, point.getNeighbors(true).size());
            assertEquals(expected, point.getNeighbors(true).stream().distinct().count());
        }
    }

}
