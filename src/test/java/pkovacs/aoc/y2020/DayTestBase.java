package pkovacs.aoc.y2020;

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

    void assertSolution1(String expected) {
        assertSolution(1, expected);

    }

    void assertSolution2(String expected) {
        assertSolution(2, expected);
    }

    private void assertSolution(int puzzle, String expected) {
        var output = outputStream.toString(StandardCharsets.UTF_8);
        var parts = output.split(System.lineSeparator())[puzzle - 1].split(": ");
        var value = parts.length < 2 ? "" : parts[1].trim();
        Assertions.assertEquals(expected, value);
    }

}
