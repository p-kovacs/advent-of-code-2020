package pkovacs.aoc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

abstract class DayTestBase {

    private final PrintStream origOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void changeSystemOut() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(origOut);
    }

    void assertSolution1(long expected) {
        assertSolution(1, expected);

    }

    void assertSolution2(long expected) {
        assertSolution(2, expected);
    }

    private void assertSolution(int puzzle, long expected) {
        var output = outputStream.toString(StandardCharsets.UTF_8);
        var parts = output.split(System.lineSeparator())[puzzle - 1].split(": ");
        long value = parts.length < 2 ? 0 : Long.parseLong(parts[1]);
        Assertions.assertEquals(expected, value);
    }

}
