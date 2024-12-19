package com.github.pkovacs.aoc.y2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.pkovacs.aoc.util.InputUtils;

public class Day24 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day24.txt");

        Set<HexTile> set = new HashSet<>();
        var center = new HexTile(0, 0);
        for (var line : lines) {
            var tile = center.getTile(line);
            if (!set.add(tile)) {
                set.remove(tile);
            }
        }
        int solution1 = set.size();

        for (int i = 0; i < 100; i++) {
            var s = set;
            set = set.stream()
                    .flatMap(tile -> tile.getNeighbors(true).stream())
                    .distinct()
                    .filter(tile -> {
                        boolean black = s.contains(tile);
                        int cnt = countBlackNeighbors(tile, s);
                        return (black && (cnt == 1 || cnt == 2)) || (!black && cnt == 2);
                    }).collect(Collectors.toSet());
        }
        int solution2 = set.size();

        System.out.println("Part 1: " + solution1);
        System.out.println("Part 2: " + solution2);
    }

    private static int countBlackNeighbors(HexTile tile, Set<HexTile> set) {
        return (int) tile.getNeighbors(false).stream().filter(set::contains).count();
    }

    /**
     * Represents a hexagonal tile as an immutable pair of int values: row index and column index.
     */
    private record HexTile(int row, int col) {

        private static final String[] DIRECTIONS = new String[] { "w", "e", "nw", "ne", "sw", "se" };

        HexTile getNeighbor(String dir) {
            return switch (dir) {
                case "w" -> new HexTile(row, col - 1);
                case "e" -> new HexTile(row, col + 1);
                case "nw" -> new HexTile(row - 1, col);
                case "ne" -> new HexTile(row - 1, col + 1);
                case "sw" -> new HexTile(row + 1, col - 1);
                case "se" -> new HexTile(row + 1, col);
                default -> throw new IllegalArgumentException();
            };
        }

        HexTile getTile(String path) {
            HexTile current = this;
            for (int i = 0; i < path.length(); i++) {
                if (path.charAt(i) == 'n' || path.charAt(i) == 's') {
                    current = current.getNeighbor(path.substring(i, i + 2));
                    i++;
                } else {
                    current = current.getNeighbor(path.substring(i, i + 1));
                }
            }
            return current;
        }

        List<HexTile> getNeighbors(boolean includeSelf) {
            var neighbors = new ArrayList<HexTile>();
            if (includeSelf) {
                neighbors.add(this);
            }
            for (var dir : DIRECTIONS) {
                neighbors.add(getNeighbor(dir));
            }
            return neighbors;
        }

    }

}
