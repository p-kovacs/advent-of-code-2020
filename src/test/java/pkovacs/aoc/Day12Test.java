package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day12Test extends DayTestBase {

    @Test
    public void test() {
        Day12.main(null);
        assertSolution1(1496);
        assertSolution2(63843);
    }

}
