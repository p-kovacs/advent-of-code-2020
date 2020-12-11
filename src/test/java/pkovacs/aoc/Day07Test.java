package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day07Test extends DayTestBase {

    @Test
    public void test() {
        Day07.main(null);
        assertSolution1(148);
        assertSolution2(24867);
    }

}
