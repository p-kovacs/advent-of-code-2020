package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day04Test extends DayTestBase {

    @Test
    public void test() {
        Day04.main(null);
        assertSolution1("237");
        assertSolution2("172");
    }

}
