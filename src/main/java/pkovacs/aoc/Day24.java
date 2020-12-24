package pkovacs.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import pkovacs.aoc.util.InputUtils;

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
                    .filter(tile -> {
                        int cnt = tile.countNeighbors(s::contains);
                        return (s.contains(tile) && (cnt == 1 || cnt == 2)) || (!s.contains(tile) && cnt == 2);
                    }).collect(Collectors.toSet());
        }
        int solution2 = set.size();

        System.out.println("Part 1: " + solution1);
        System.out.println("Part 2: " + solution2);
    }

    private static class HexTile {

        int row;
        int col;

        public HexTile(int row, int col) {
            this.col = col;
            this.row = row;
        }

        HexTile getNeighbor(String dir) {
            if ("w".equals(dir)) {
                return new HexTile(row, col - 1);
            } else if ("e".equals(dir)) {
                return new HexTile(row, col + 1);
            } else if ("nw".equals(dir)) {
                return new HexTile(row - 1, col);
            } else if ("ne".equals(dir)) {
                return new HexTile(row - 1, col + 1);
            } else if ("sw".equals(dir)) {
                return new HexTile(row + 1, col - 1);
            } else if ("se".equals(dir)) {
                return new HexTile(row + 1, col);
            }
            throw new IllegalArgumentException();
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
            for (var dir : new String[] { "w", "e", "nw", "ne", "sw", "se" }) {
                neighbors.add(getNeighbor(dir));
            }
            return neighbors;
        }

        int countNeighbors(Predicate<HexTile> predicate) {
            return (int) getNeighbors(false).stream().filter(predicate).count();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            HexTile other = (HexTile) o;
            return col == other.col && row == other.row;
        }

        @Override
        public int hashCode() {
            return Objects.hash(col, row);
        }

        @Override
        public String toString() {
            return "(" + col + "," + row + ')';
        }

    }

}
