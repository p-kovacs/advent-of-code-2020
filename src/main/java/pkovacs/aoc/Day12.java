package pkovacs.aoc;

import java.util.List;

import pkovacs.aoc.util.AocUtils;

public class Day12 {

    public static void main(String[] args) {
        List<String> lines = AocUtils.readLines("day12.txt");

        System.out.println("Puzzle 1: " + solvePuzzle1(lines));
        System.out.println("Puzzle 2: " + solvePuzzle2(lines));
    }

    private static long solvePuzzle1(List<String> lines) {
        long x = 0, y = 0;
        long wx = 1, wy = 0;
        for (var line : lines) {
            char ch = line.charAt(0);
            var v = Long.parseLong(line.substring(1));
            if (ch == 'F') {
                x += wx * v;
                y += wy * v;
            } else if (ch == 'N') {
                y -= v;
            } else if (ch == 'S') {
                y += v;
            } else if (ch == 'E') {
                x += v;
            } else if (ch == 'W') {
                x -= v;
            } else if (ch == 'L' || ch == 'R') {
                int rightTurns = (int) (ch == 'R' ? v : 360 - v) / 90;
                for (int i = 0; i < rightTurns; i++) {
                    long oldx = wx;
                    wx = -1 * wy;
                    wy = oldx;
                }
            }
        }
        return Math.abs(x) + Math.abs(y);
    }

    private static long solvePuzzle2(List<String> lines) {
        long x = 0, y = 0;
        long wx = 10, wy = -1;
        for (var line : lines) {
            char ch = line.charAt(0);
            var v = Long.parseLong(line.substring(1));
            if (ch == 'F') {
                x += wx * v;
                y += wy * v;
            } else if (ch == 'N') {
                wy -= v;
            } else if (ch == 'S') {
                wy += v;
            } else if (ch == 'E') {
                wx += v;
            } else if (ch == 'W') {
                wx -= v;
            } else if (ch == 'L' || ch == 'R') {
                int rightTurns = (int) (ch == 'R' ? v : 360 - v) / 90;
                for (int i = 0; i < rightTurns; i++) {
                    long oldx = wx;
                    wx = -1 * wy;
                    wy = oldx;
                }
            }
        }
        return Math.abs(x) + Math.abs(y);
    }

}
