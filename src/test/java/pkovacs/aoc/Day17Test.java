package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day17Test extends DayTestBase {

    @Test
    public void test() {
        Day17.main(null);
        assertSolution1("286");
        assertSolution2("960");
    }

}
