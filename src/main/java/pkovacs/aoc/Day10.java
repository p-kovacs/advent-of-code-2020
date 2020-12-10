package pkovacs.aoc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import pkovacs.aoc.util.AocUtils;

public class Day10 {

    public static void main(String[] args) {
        List<String> lines = AocUtils.readLines("day10.txt");

        var x = new long[lines.size() + 2];
        for (int i = 0; i < lines.size(); i++) {
            x[i] = Long.parseLong(lines.get(i));
        }
        x[lines.size()] = 0;
        x[lines.size() + 1] = Arrays.stream(x).max().getAsLong() + 3;
        Arrays.sort(x);

        System.out.println("Puzzle 1: " + countDiff(x, 1) * countDiff(x, 3));
        System.out.println("Puzzle 2: " + getPathCount(x));
    }

    private static long countDiff(long[] x, long diff) {
        return IntStream.range(1, x.length).filter(i -> x[i] - x[i - 1] == diff).count();
    }

    private static long getPathCount(long[] x) {
        long[] pathCount = new long[x.length];
        pathCount[0] = 1;
        for (int i = 1; i < x.length; i++) {
            for (int j = i - 3; j < i; j++) {
                if (j >= 0 && x[i] - x[j] <= 3) {
                    pathCount[i] += pathCount[j];
                }
            }
        }
        return pathCount[pathCount.length - 1];
    }

}
