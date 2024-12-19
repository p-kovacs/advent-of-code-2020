package com.github.pkovacs.aoc.y2020;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.pkovacs.aoc.util.InputUtils;
import com.github.pkovacs.aoc.util.Vector;

public class Day17 {

    public static void main(String[] args) {
        var input = InputUtils.readCharMatrix("day17.txt");

        int cycleCount = 6;
        System.out.println("Part 1: " + solve(input, 3, cycleCount));
        System.out.println("Part 2: " + solve(input, 4, cycleCount));
    }

    private static long solve(char[][] input, int dim, int cycleCount) {
        Set<Vector> actives = new HashSet<>();
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[row].length; col++) {
                if (input[row][col] == '#') {
                    actives.add(createVector(dim, col, row));
                }
            }
        }

        for (int c = 0; c < cycleCount; c++) {
            var set = actives;
            actives = actives.stream()
                    .flatMap(p -> p.getNeighborsAndSelf().stream())
                    .distinct()
                    .filter(p -> {
                        boolean active = set.contains(p);
                        long cnt = countActiveNeighbors(p, set);
                        return (active && (cnt == 2 || cnt == 3)) || (!active && cnt == 3);
                    })
                    .collect(Collectors.toSet());
        }

        return actives.size();
    }

    private static Vector createVector(int dim, int x, int y) {
        var coords = new int[dim];
        coords[0] = x;
        coords[1] = y;
        return new Vector(coords);
    }

    private static int countActiveNeighbors(Vector vector, Set<Vector> actives) {
        return (int) vector.getNeighbors().stream().filter(actives::contains).count();
    }

}
