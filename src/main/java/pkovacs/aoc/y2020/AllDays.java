package pkovacs.aoc.y2020;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

/**
 * Verifies the solution for each day against the expected answers for my puzzle input files.
 */
public class AllDays {

    private static final List<Day> DAYS = List.of(
            new Day("Day 01", Day01::main, "539851", "212481360"),
            new Day("Day 02", Day02::main, "666", "670"),
            new Day("Day 03", Day03::main, "218", "3847183340"),
            new Day("Day 04", Day04::main, "237", "172"),
            new Day("Day 05", Day05::main, "871", "640"),
            new Day("Day 06", Day06::main, "6625", "3360"),
            new Day("Day 07", Day07::main, "148", "24867"),
            new Day("Day 08", Day08::main, "1867", "1303"),
            new Day("Day 09", Day09::main, "26134589", "3535124"),
            new Day("Day 10", Day10::main, "2346", "6044831973376"),
            new Day("Day 11", Day11::main, "2283", "2054"),
            new Day("Day 12", Day12::main, "1496", "63843"),
            new Day("Day 13", Day13::main, "2238", "560214575859998"),
            new Day("Day 14", Day14::main, "17028179706934", "3683236147222"),
            new Day("Day 15", Day15::main, "249", "41687"),
            new Day("Day 16", Day16::main, "26869", "855275529001"),
            new Day("Day 17", Day17::main, "286", "960"),
            new Day("Day 18", Day18::main, "21022630974613", "169899524778212"),
            new Day("Day 19", Day19::main, "107", "321"),
            new Day("Day 20", Day20::main, "11788777383197", "2242"),
            new Day("Day 21", Day21::main, "1977", "dpkvsdk,xmmpt,cxjqxbt,drbq,zmzq,mnrjrf,kjgl,rkcpxs"),
            new Day("Day 22", Day22::main, "35005", "32751"),
            new Day("Day 23", Day23::main, "28946753", "519044017360"),
            new Day("Day 24", Day24::main, "332", "3900"),
            new Day("Day 25", Day25::main, "6408263", "0")
    );

    public static void main(String[] args) {
        String format = "%-12s%-8s%-8s%8s%n";
        System.out.printf(format, "Day", "Part 1", "Part 2", "Time");

        DAYS.stream().filter(day -> day.mainMethod != null).forEach(day -> {
            long start = System.nanoTime();
            var results = runDay(day);
            long time = (System.nanoTime() - start) / 1_000_000L;

            System.out.printf(format, day.name, evaluate(day, results, 0), evaluate(day, results, 1), time + " ms");
        });
    }

    private static String evaluate(Day day, List<String> results, int index) {
        var expected = index == 0 ? day.expected1 : day.expected2;
        return results.size() == 2 && expected.equals(results.get(index)) ? "\u2714" : "FAILED";
    }

    private static List<String> runDay(Day day) {
        var origOut = System.out;
        try {
            var out = new ByteArrayOutputStream(200);
            System.setOut(new PrintStream(out));
            day.mainMethod.accept(null);
            return out.toString(StandardCharsets.UTF_8).lines().map(l -> l.split(": ")[1]).toList();
        } catch (Exception e) {
            return List.of();
        } finally {
            System.setOut(origOut);
        }
    }

    private record Day(String name, Consumer<String[]> mainMethod, String expected1, String expected2) {}

}
