package pkovacs.aoc;

import pkovacs.aoc.util.InputUtils;

public class Day01 {

    public static void main(String[] args) {
        var data = InputUtils.readLongs("day01.txt");

        long solution1 = 0;
        long solution2 = 0;
        for (int i = 0; i < data.length; i++) {
            long x = data[i];
            for (int j = i + 1; j < data.length; j++) {
                long y = data[j];
                if (x + y == 2020) {
                    solution1 = x * y;
                }
                for (int k = j + 1; k < data.length; k++) {
                    long z = data[k];
                    if (x + y + z == 2020) {
                        solution2 = x * y * z;
                    }
                }
            }
        }

        System.out.println("Part 1: " + solution1);
        System.out.println("Part 2: " + solution2);
    }

}
