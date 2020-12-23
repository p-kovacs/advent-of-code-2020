package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day23Test extends DayTestBase {

    @Test
    public void test() {
        Day23.main(null);
        assertSolution1("28946753");
        assertSolution2("519044017360");
    }

}
