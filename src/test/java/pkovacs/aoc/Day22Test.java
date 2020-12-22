package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day22Test extends DayTestBase {

    @Test
    public void test() {
        Day22.main(null);
        assertSolution1("35005");
        assertSolution2("32751");
    }

}
