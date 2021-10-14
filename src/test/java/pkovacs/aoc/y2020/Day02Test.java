package pkovacs.aoc.y2020;

import org.junit.jupiter.api.Test;

class Day02Test extends DayTestBase {

    @Test
    public void test() {
        Day02.main(null);
        assertSolution1("666");
        assertSolution2("670");
    }

}
