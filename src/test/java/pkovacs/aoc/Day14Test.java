package pkovacs.aoc;

import org.junit.jupiter.api.Test;

class Day14Test extends DayTestBase {

    @Test
    public void test() {
        Day14.main(null);
        assertSolution1(17028179706934L);
        assertSolution2(3683236147222L);
    }

}
