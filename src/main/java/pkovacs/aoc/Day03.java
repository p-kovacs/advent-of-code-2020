package pkovacs.aoc;

import java.util.List;

import pkovacs.aoc.util.AocUtils;

public class Day03 {

    public static void main(String[] args) {
        List<String> map = AocUtils.readLines("day03.txt");

        System.out.println("Puzzle 1: " + solve(map, new int[] { 3 }, new int[] { 1 }));
        System.out.println("Puzzle 2: " + solve(map, new int[] { 1, 3, 5, 7, 1 }, new int[] { 1, 1, 1, 1, 2 }));
    }

    private static long solve(List<String> map, int[] right, int[] down) {
        long result = 1;
        for (int i = 0; i < right.length; i++) {
            int x = 0, y = 0;
            long cnt = 0;
            while (y < map.size()) {
                if (map.get(y).charAt(x) == '#') {
                    cnt++;
                }
                x = (x + right[i]) % map.get(y).length();
                y += down[i];
            }
            result *= cnt;
        }
        return result;
    }

}
