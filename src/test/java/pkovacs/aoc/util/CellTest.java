package pkovacs.aoc.util;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTest {

    @Test
    public void test() {
        var c = new Cell(12, 42);
        var d = new Cell(42, 12);
        var e = new Cell(42, 12);

        assertEquals(12, c.row);
        assertEquals(42, c.col);
        assertNotEquals(c, d);
        assertEquals(d, e);

        assertTrue(c.isValid(13, 43));
        assertFalse(c.isValid(12, 43));
        assertFalse(c.isValid(13, 42));

        assertEquals(List.of(new Cell(11, 42), new Cell(12, 41), new Cell(12, 43),
                new Cell(13, 42)), c.getFourNeighbors());

        assertEquals(List.of(new Cell(11, 41), new Cell(11, 42), new Cell(11, 43),
                new Cell(12, 41), new Cell(12, 43),
                new Cell(13, 41), new Cell(13, 42), new Cell(13, 43)),
                c.getEightNeighbors());
    }

}
