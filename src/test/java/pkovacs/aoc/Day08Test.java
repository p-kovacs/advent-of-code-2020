package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day08Test extends DayTestBase {

    @Test
    public void test() {
        Day08.main(null);
        assertSolution1(1867);
        assertSolution2(1303);
    }

}
