package pkovacs.aoc.util;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HexTileTest {

    @Test
    public void test() {
        var c = new HexTile(12, 42);
        var d = new HexTile(42, 12);
        var e = new HexTile(42, 12);

        assertEquals(12, c.row);
        assertEquals(42, c.col);
        assertNotEquals(c, d);
        assertEquals(d, e);

        assertEquals(c, c.getTile("nwwswee"));
        assertEquals(new HexTile(13, 42), c.getTile("esew"));

        assertEquals(List.of(new HexTile(12, 41), new HexTile(12, 43),
                new HexTile(11, 42), new HexTile(11, 43),
                new HexTile(13, 41), new HexTile(13, 42)), c.getNeighbors());
        assertEquals(List.of(new HexTile(12, 42), new HexTile(12, 41), new HexTile(12, 43),
                new HexTile(11, 42), new HexTile(11, 43),
                new HexTile(13, 41), new HexTile(13, 42)), c.getNeighbors(true));
    }

}
