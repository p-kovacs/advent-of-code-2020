package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day20Test extends DayTestBase {

    @Test
    public void test() {
        Day20.main(null);
        assertSolution1(11788777383197L);
        assertSolution2(2242);
    }

}
