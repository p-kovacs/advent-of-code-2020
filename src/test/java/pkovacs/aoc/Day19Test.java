package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day19Test extends DayTestBase {

    @Test
    public void test() {
        Day19.main(null);
        assertSolution1("107");
        assertSolution2("321");
    }

}
