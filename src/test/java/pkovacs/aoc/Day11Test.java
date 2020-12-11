package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day11Test extends DayTestBase {

    @Test
    public void test() {
        Day11.main(null);
        assertSolution1(2283);
        assertSolution2(2054);
    }

}
