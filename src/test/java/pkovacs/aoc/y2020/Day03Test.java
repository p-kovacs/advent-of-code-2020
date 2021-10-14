package pkovacs.aoc.y2020;

import org.junit.jupiter.api.Test;

class Day03Test extends DayTestBase {

    @Test
    public void test() {
        Day03.main(null);
        assertSolution1("218");
        assertSolution2("3847183340");
    }

}
