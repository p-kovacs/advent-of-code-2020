package pkovacs.aoc.y2020;

import java.util.ArrayList;
import java.util.List;

import pkovacs.aoc.util.CounterMap;
import pkovacs.aoc.util.InputUtils;

public class Day14 {

    public static void main(String[] args) {
        List<String> lines = InputUtils.readLines("day14.txt");

        System.out.println("Part 1: " + solvePuzzle(lines, false));
        System.out.println("Part 2: " + solvePuzzle(lines, true));
    }

    private static long solvePuzzle(List<String> lines, boolean second) {
        var mask = "";
        var mem = new CounterMap<Long>();
        for (String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.split(" = ")[1];
                continue;
            }

            var parts = InputUtils.scan(line, "mem\\[%d\\] = %d");
            var index = parts.get(0).asLong();
            var value = parts.get(1).asLong();

            var indices = new ArrayList<Long>();
            indices.add(index);
            for (int k = 0; k < mask.length(); k++) {
                var ch = mask.charAt(mask.length() - 1 - k);
                if (second) {
                    if (ch == '1') {
                        for (int j = 0; j < indices.size(); j++) {
                            indices.set(j, setBit(indices.get(j), k, true));
                        }
                    } else if (ch == 'X') {
                        var newIndices = new ArrayList<Long>();
                        for (long i : indices) {
                            newIndices.add(setBit(i, k, true));
                            newIndices.add(setBit(i, k, false));
                        }
                        indices = newIndices;
                    }
                } else {
                    if (ch == '0' || ch == '1') {
                        value = setBit(value, k, ch == '1');
                    }
                }
            }

            for (long i : indices) {
                mem.put(i, value);
            }
        }

        return mem.sum();
    }

    private static long setBit(long value, int bitPos, boolean bit) {
        return bit ? value | (1L << bitPos) : value & ~(1L << bitPos);
    }

}
