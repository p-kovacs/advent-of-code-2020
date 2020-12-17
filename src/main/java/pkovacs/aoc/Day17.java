package pkovacs.aoc;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.Point;

public class Day17 {

    public static void main(String[] args) {
        var input = InputUtils.readCharMatrix("day17.txt");

        int cycleCount = 6;
        System.out.println("Part 1: " + solve(input, 3, cycleCount));
        System.out.println("Part 2: " + solve(input, 4, cycleCount));
    }

    private static long solve(char[][] input, int dim, int cycleCount) {
        var actives = new HashSet<pkovacs.aoc.util.Point>();

        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[row].length; col++) {
                if (input[row][col] == '#') {
                    actives.add(createPoint(dim, col, row));
                }
            }
        }

        for (int c = 0; c < cycleCount; c++) {
            var newActives = actives.stream()
                    .flatMap(p -> p.getNeighbors(true).stream())
                    .distinct()
                    .filter(p -> {
                        boolean active = actives.contains(p);
                        long nc = countActiveNeighbors(actives, p);
                        return (active && (nc == 2 || nc == 3)) || (!active && nc == 3);
                    })
                    .collect(Collectors.toSet());
            actives.clear();
            actives.addAll(newActives);
        }

        return actives.size();
    }

    private static Point createPoint(int dim, int x, int y) {
        var coords = new int[dim];
        coords[0] = x;
        coords[1] = y;
        return new Point(coords);
    }

    private static long countActiveNeighbors(Set<Point> actives, Point point) {
        return point.getNeighbors().stream()
                .filter(actives::contains)
                .count();
    }

}
