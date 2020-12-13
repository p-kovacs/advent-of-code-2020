package pkovacs.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputUtilsTest {

    @Test
    public void testCollectLineBlocks() {
        String input = "a\nb c d\ne\n\nf g\nh\n\ni j k";
        var blocks = InputUtils.collectLineBlocks(input);

        assertEquals(3, blocks.size());
        assertArrayEquals(new String[] { "a", "b c d", "e" }, blocks.get(0));
        assertArrayEquals(new String[] { "f g", "h" }, blocks.get(1));
        assertArrayEquals(new String[] { "i j k" }, blocks.get(2));

        assertEquals(3, InputUtils.collectLineBlocks(input + "\n").size());
        assertEquals(3, InputUtils.collectLineBlocks(input + "\n\n").size());
        assertEquals(4, InputUtils.collectLineBlocks(input + "\n\n\n").size());
    }

    @Test
    public void testScan() {
        var values = InputUtils.scan("Product PID_4242X is ordered.", ".*PID_%d%c is %s[.]");

        assertEquals(3, values.size());
        assertTrue(values.get(0).isInteger());
        assertEquals(4242, values.get(0).asInt());
        assertTrue(values.get(1).isChar());
        assertEquals('X', values.get(1).asChar());
        assertTrue(values.get(2).isString());
        assertEquals("ordered", values.get(2).get());

        assertEquals("[ParsedValue(Long: 4242), ParsedValue(Character: X), ParsedValue(String: ordered)]",
                values.toString());
    }

}
