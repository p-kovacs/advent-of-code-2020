package pkovacs.aoc;

import java.util.Arrays;
import java.util.List;

import pkovacs.aoc.util.InputUtils;

public class Day05 {

    public static void main(String[] args) {
        List<String> lines = InputUtils.readLines("day05.txt");

        int[] seatIds = lines.stream().mapToInt(Day05::getSeatId).sorted().toArray();

        System.out.println("Part 1: " + Arrays.stream(seatIds).max().getAsInt());
        System.out.println("Part 2: " + findHole(seatIds));
    }

    private static int getSeatId(String s) {
        var row = s.substring(0, 7);
        var col = s.substring(7, 10);
        return getBinaryValue(row, 'B') * 8 + getBinaryValue(col, 'R');
    }

    private static int getBinaryValue(String s, char one) {
        int value = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            if (s.charAt(i) == one) {
                value |= 1 << (len - 1 - i);
            }
        }
        return value;
    }

    private static int findHole(int[] sortedArray) {
        for (int i = 0; i < sortedArray.length - 1; i++) {
            if (sortedArray[i] + 1 < sortedArray[i + 1]) {
                return sortedArray[i] + 1;
            }
        }
        return -1; // should not occur
    }

}
