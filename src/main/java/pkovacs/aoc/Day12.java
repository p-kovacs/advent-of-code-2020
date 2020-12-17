package pkovacs.aoc;

import java.util.List;

import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.Vector;

public class Day12 {

    public static void main(String[] args) {
        List<String> lines = InputUtils.readLines("day12.txt");

        System.out.println("Part 1: " + solvePuzzle(lines, false));
        System.out.println("Part 2: " + solvePuzzle(lines, true));
    }

    private static long solvePuzzle(List<String> lines, boolean second) {
        var ship = new Position(0, 0);
        var waypoint = second ? new Position(10, 1) : new Position(1, 0);
        for (var line : lines) {
            char ch = line.charAt(0);
            int v = Integer.parseInt(line.substring(1));
            if (ch == 'F') {
                ship.add(waypoint.x * v, waypoint.y * v);
            } else if (ch == 'N' || ch == 'S' || ch == 'E' || ch == 'W') {
                (second ? waypoint : ship).move(ch, v);
            } else if (ch == 'L' || ch == 'R') {
                waypoint.rotate(ch, v);
            }
        }
        return ship.length();
    }

    private static class Position extends Vector {

        public Position(long x, long y) {
            super(x, y);
        }

        void move(char dir, int value) {
            if (dir == 'N') {
                y += value;
            } else if (dir == 'S') {
                y -= value;
            } else if (dir == 'E') {
                x += value;
            } else if (dir == 'W') {
                x -= value;
            } else {
                throw new IllegalArgumentException("Unknown direction for move: " + dir);
            }
        }

        void rotate(char dir, int degree) {
            int rightTurns = (dir == 'R' ? degree : 360 - degree) / 90;
            for (int i = 0; i < rightTurns; i++) {
                rotateRight();
            }
        }

    }

}
