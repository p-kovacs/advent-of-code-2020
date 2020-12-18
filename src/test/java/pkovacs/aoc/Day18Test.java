package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day18Test extends DayTestBase {

    @Test
    public void test() {
        Day18.main(null);
        assertSolution1(21022630974613L);
        assertSolution2(169899524778212L);
    }

}
