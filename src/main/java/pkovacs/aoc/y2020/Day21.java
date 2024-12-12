package pkovacs.aoc.y2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import pkovacs.aoc.util.InputUtils;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class Day21 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day21.txt");

        // Process input
        var ingsPerLine = new ArrayList<Set<String>>();
        var allergsPerLine = new ArrayList<Set<String>>();
        SetMultimap<String, String> canContain = MultimapBuilder.hashKeys().hashSetValues().build();
        for (var line : lines) {
            var parts = line.split(" \\(contains ");
            var ings = Arrays.stream(parts[0].split(" ")).collect(toSet());
            var allergs = Arrays.stream(parts[1].split("\\)")[0].split(", ")).collect(toSet());
            ingsPerLine.add(ings);
            allergsPerLine.add(allergs);
            for (var k : ings) {
                for (var v : allergs) {
                    canContain.put(k, v);
                }
            }
        }

        // Remove impossible assignments
        var allIngs = new HashSet<>(canContain.keySet());
        var allAllergs = new HashSet<>(canContain.values());
        for (var e : new ArrayList<>(canContain.entries())) {
            for (int i = 0; i < ingsPerLine.size(); i++) {
                if (allergsPerLine.get(i).contains(e.getValue()) && !ingsPerLine.get(i).contains(e.getKey())) {
                    canContain.remove(e.getKey(), e.getValue());
                }
            }
        }

        // Solve part 1
        var freeIngs = allIngs.stream().filter(k -> !canContain.containsKey(k)).collect(toSet());
        long solution1 = ingsPerLine.stream().flatMap(Collection::stream).filter(freeIngs::contains).count();

        // Map ingredients to allergens with greedy method
        var contains = new HashMap<String, String>();
        while (!canContain.isEmpty()) {
            var next = canContain.keySet().stream()
                    .filter(k -> canContain.get(k).size() == 1).findAny();
            if (next.isEmpty()) {
                throw new RuntimeException("Greedy assignment failed.");
            }

            var v = canContain.get(next.get()).iterator().next();
            contains.put(next.get(), v);
            canContain.entries().removeIf(e -> e.getValue().equals(v));
        }

        // Solve part 2
        var solution2 = contains.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(joining(","));

        System.out.println("Part 1: " + solution1);
        System.out.println("Part 2: " + solution2);
    }

}
