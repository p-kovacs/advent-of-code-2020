package pkovacs.aoc;

import java.util.List;

import pkovacs.aoc.util.InputUtils;

public class Day13 {

    public static void main(String[] args) {
        List<String> lines = InputUtils.readLines("day13.txt");

        long timestamp = Long.parseLong(lines.get(0));
        long[] buses = readBusArray(lines.get(1));

        System.out.println("Puzzle 1: " + solvePuzzle1(buses, timestamp));
        System.out.println("Puzzle 2: " + findEarliestDepartureTime(buses));
    }

    private static long[] readBusArray(String line) {
        var parts = line.split(",");
        long[] buses = new long[parts.length];
        for (int i = 0; i < buses.length; i++) {
            buses[i] = "x".equals(parts[i]) ? -1 : Long.parseLong(parts[i]);
        }
        return buses;
    }

    private static long solvePuzzle1(long[] buses, long timestamp) {
        long min = Long.MAX_VALUE;
        long sol = 0;
        for (var n : buses) {
            if (n < 0) {
                continue;
            }
            var next = ((timestamp + n - 1) / n) * n;
            if (next - timestamp < min) {
                min = next - timestamp;
                sol = n * min;
            }
        }
        return sol;
    }

    /**
     * Solves puzzle 2 of the day.
     * We search for the smallest number x such that:
     * for each i where n = buses[i] > -1: (x + i) % n == 0 (i.e. x and n - i are congruent modulo n)
     * <p>
     * In fact, we should apply an ancient theorem/algorithm:
     * https://en.wikipedia.org/wiki/Chinese_remainder_theorem
     * For this, the values buses[i] must be pairwise coprime, but I guess that they are prime numbers
     * in everyone's input, so let's suppose this.
     */
    private static long findEarliestDepartureTime(long[] buses) {
        long x = 0;
        long step = 1;
        for (int i = 0; i < buses.length; i++) {
            if (buses[i] < 0) {
                continue;
            }
            long n = buses[i];
            while ((x + i) % n != 0) {
                x += step;
            }
            step *= n;
        }
        return x;
    }

}