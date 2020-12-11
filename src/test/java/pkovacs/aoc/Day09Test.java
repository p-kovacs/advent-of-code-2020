package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day09Test extends DayTestBase {

    @Test
    public void test() {
        Day09.main(null);
        assertSolution1(26134589);
        assertSolution2(3535124);
    }

}
