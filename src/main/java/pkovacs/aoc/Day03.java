package pkovacs.aoc;

import pkovacs.aoc.util.InputUtils;

public class Day03 {

    public static void main(String[] args) {
        char[][] map = InputUtils.readCharMatrix("day03.txt");

        System.out.println("Puzzle 1: " + solve(map, new int[] { 3 }, new int[] { 1 }));
        System.out.println("Puzzle 2: " + solve(map, new int[] { 1, 3, 5, 7, 1 }, new int[] { 1, 1, 1, 1, 2 }));
    }

    private static long solve(char[][] map, int[] right, int[] down) {
        long result = 1;
        for (int i = 0; i < right.length; i++) {
            int c = 0, r = 0;
            long cnt = 0;
            while (r < map.length) {
                if (map[r][c] == '#') {
                    cnt++;
                }
                c = (c + right[i]) % map[r].length;
                r += down[i];
            }
            result *= cnt;
        }
        return result;
    }

}
