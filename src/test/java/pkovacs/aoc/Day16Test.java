package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day16Test extends DayTestBase {

    @Test
    public void test() {
        Day16.main(null);
        assertSolution1(26869);
        assertSolution2(855275529001L);
    }

}
