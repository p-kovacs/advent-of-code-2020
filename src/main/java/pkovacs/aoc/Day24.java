package pkovacs.aoc;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import pkovacs.aoc.util.HexTile;
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
                        int cnt = countNeighbors(tile, s::contains);
                        return (s.contains(tile) && (cnt == 1 || cnt == 2)) || (!s.contains(tile) && cnt == 2);
                    }).collect(Collectors.toSet());
        }
        int solution2 = set.size();

        System.out.println("Part 1: " + solution1);
        System.out.println("Part 2: " + solution2);
    }

    private static int countNeighbors(HexTile tile, Predicate<HexTile> predicate) {
        return (int) tile.getNeighbors().stream().filter(predicate).count();
    }

}
