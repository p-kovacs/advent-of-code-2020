package pkovacs.aoc.y2020;

import org.junit.jupiter.api.Test;

class Day05Test extends DayTestBase {

    @Test
    public void test() {
        Day05.main(null);
        assertSolution1("871");
        assertSolution2("640");
    }

}
