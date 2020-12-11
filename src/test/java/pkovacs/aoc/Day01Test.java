package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day01Test extends DayTestBase {

    @Test
    public void test() {
        Day01.main(null);
        assertSolution1(539851);
        assertSolution2(212481360);
    }

}
