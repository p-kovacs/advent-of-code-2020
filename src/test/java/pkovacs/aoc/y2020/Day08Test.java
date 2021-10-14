package pkovacs.aoc.y2020;

import org.junit.jupiter.api.Test;

class Day08Test extends DayTestBase {

    @Test
    public void test() {
        Day08.main(null);
        assertSolution1("1867");
        assertSolution2("1303");
    }

}
