package com.github.pkovacs.aoc.y2020;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.github.pkovacs.aoc.util.InputUtils;

public class Day09 {

    public static void main(String[] args) {
        var x = InputUtils.readLongs("day09.txt");

        int preambleSize = 25;
        int index = IntStream.range(preambleSize, x.length)
                .filter(i -> !isValid(x, i, preambleSize))
                .findFirst().getAsInt();

        System.out.println("Part 1: " + x[index]);
        System.out.println("Part 2: " + getMinMaxSum(x, index));
    }

    private static boolean isValid(long[] x, int i, int preambleSize) {
        for (int j = i - preambleSize; j < i; j++) {
            for (int k = j + 1; k < i; k++) {
                if (x[j] + x[k] == x[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static long getMinMaxSum(long[] x, int i) {
        for (int from = 0; from < i; from++) {
            for (int to = from + 2; to <= i; to++) {
                long sum = Arrays.stream(x, from, to).sum();
                if (sum == x[i]) {
                    return Arrays.stream(x, from, to).min().getAsLong()
                            + Arrays.stream(x, from, to).max().getAsLong();
                }
            }
        }
        return -1; // should not happen
    }

}
