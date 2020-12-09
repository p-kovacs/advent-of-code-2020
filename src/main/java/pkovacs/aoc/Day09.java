package pkovacs.aoc;

import java.util.List;
import java.util.stream.IntStream;

import pkovacs.aoc.util.AocUtils;

public class Day09 {

    public static void main(String[] args) {
        List<String> lines = AocUtils.readLines("day09.txt");
        var x = new long[lines.size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = Long.parseLong(lines.get(i));
        }

        int preambleSize = 25;
        int index = IntStream.range(preambleSize, x.length)
                .filter(i -> !isValid(x, i, preambleSize))
                .findFirst().getAsInt();

        System.out.println("Puzzle 1: " + x[index]);
        System.out.println("Puzzle 2: " + getMinMaxSum(x, index));
    }

    private static boolean isValid(long[] x, int i, int preambleSize) {
        for (int j = i - preambleSize; j < i; j++) {
            for (int k = j + 1; k < i; k++) {
                if (x[i] == x[j] + x[k]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static long getMinMaxSum(long[] x, int i) {
        for (int j = 0; j < i; j++) {
            for (int k = j + 1; k < i; k++) {
                long sum = IntStream.rangeClosed(j, k).mapToLong(a -> x[a]).sum();
                if (sum == x[i]) {
                    return IntStream.rangeClosed(j, k).mapToLong(a -> x[a]).min().getAsLong()
                            + IntStream.rangeClosed(j, k).mapToLong(a -> x[a]).max().getAsLong();
                }
            }
        }
        return -1; // should not happen
    }

}
