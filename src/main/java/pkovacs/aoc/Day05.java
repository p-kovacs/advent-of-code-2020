package pkovacs.aoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Day05 {

    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.readLines(Day05.class.getResourceAsStream("day05.txt"), StandardCharsets.UTF_8);

        int[] seatIds = lines.stream().mapToInt(Day05::getSeatId).sorted().toArray();

        System.out.println("Puzzle 1: " + Arrays.stream(seatIds).max().getAsInt());
        System.out.println("Puzzle 2: " + findHole(seatIds));
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
