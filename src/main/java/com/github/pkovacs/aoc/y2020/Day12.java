package com.github.pkovacs.aoc.y2020;

import java.util.List;

import com.github.pkovacs.aoc.util.InputUtils;

public class Day12 {

    public static void main(String[] args) {
        List<String> lines = InputUtils.readLines("day12.txt");

        System.out.println("Part 1: " + solvePuzzle(lines, false));
        System.out.println("Part 2: " + solvePuzzle(lines, true));
    }

    private static long solvePuzzle(List<String> lines, boolean second) {
        var ship = new Pos(0, 0);
        var waypoint = second ? new Pos(10, 1) : new Pos(1, 0);
        for (var line : lines) {
            char ch = line.charAt(0);
            int v = Integer.parseInt(line.substring(1));
            if (ch == 'F') {
                ship = ship.add(waypoint.x * v, waypoint.y * v);
            } else if (ch == 'N' || ch == 'S' || ch == 'E' || ch == 'W') {
                if (second) {
                    waypoint = waypoint.move(ch, v);
                } else {
                    ship = ship.move(ch, v);
                }
            } else if (ch == 'L' || ch == 'R') {
                waypoint = waypoint.rotate(ch, v);
            }
        }
        return Math.abs(ship.x) + Math.abs(ship.y);
    }

    private record Pos(long x, long y) {

        Pos move(char dir, int value) {
            return switch (dir) {
                case 'N' -> new Pos(x, y + value);
                case 'S' -> new Pos(x, y - value);
                case 'E' -> new Pos(x + value, y);
                case 'W' -> new Pos(x - value, y);
                default -> throw new IllegalArgumentException("Unknown direction.");
            };
        }

        Pos add(long dx, long dy) {
            return new Pos(x + dx, y + dy);
        }

        Pos rotate(char dir, int degree) {
            int rightTurns = (dir == 'R' ? degree : 360 - degree) / 90;
            Pos p = this;
            for (int i = 0; i < rightTurns; i++) {
                p = p.rotateRight();
            }
            return p;
        }

        Pos rotateRight() {
            return new Pos(y, -x);
        }

    }

}
