package pkovacs.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pkovacs.aoc.util.AocUtils;

public class Day07 {

    public static void main(String[] args) {
        List<String> lines = AocUtils.readLines("day07.txt");
        runSolutionA(lines);
//        runSolutionB(lines);
    }

    /**
     * My original approach (after some refactoring).
     */
    private static void runSolutionA(List<String> lines) {
        var graph = new HashMap<String, List<String>>();
        var revGraph = new HashMap<String, List<String>>();
        var count = new HashMap<String, Map<String, Integer>>();
        for (var line : lines) {
            var parts = AocUtils.scan(line, "%s bag.*contain %s");
            var bag = parts.get(0).get();
            var contents = parts.get(1).get();
            if (contents.startsWith("no other")) {
                continue;
            }
            for (var content : contents.split(",")) {
                var values = AocUtils.scan(content, " *%d %s bag.*");
                var innerBag = values.get(1).get();
                graph.computeIfAbsent(bag, x -> new ArrayList<>()).add(innerBag);
                revGraph.computeIfAbsent(innerBag, x -> new ArrayList<>()).add(bag);
                count.computeIfAbsent(bag, x -> new HashMap<>()).put(innerBag, values.get(0).asInt());
            }
        }

        var s = "shiny gold";
        long puzzle1 = collectAllPaths(revGraph, s).stream()
                .map(list -> list.get(list.size() - 1))
                .distinct().count();
        long puzzle2 = collectAllPaths(graph, s).stream()
                .mapToLong(list -> {
                    long mul = 1;
                    for (int i = 0; i < list.size() - 1; i++) {
                        mul *= count.get(list.get(i)).get(list.get(i + 1));
                    }
                    return mul;
                }).sum();

        System.out.println("Puzzle 1: " + puzzle1);
        System.out.println("Puzzle 2: " + puzzle2);
    }

    private static <T> List<List<T>> collectAllPaths(Map<T, List<T>> graph, T source) {
        var results = new ArrayList<List<T>>();

        results.add(Collections.singletonList(source));
        for (int i = 0; i < results.size(); i++) {
            var path = results.get(i);
            T front = path.get(path.size() - 1);
            for (T next : graph.getOrDefault(front, Collections.emptyList())) {
                var nextPath = new ArrayList<T>(path);
                nextPath.add(next);
                results.add(nextPath);
            }
        }

        return results.subList(1, results.size()); // skip first path (to source)
    }

    /**
     * An alternative approach, influenced by the solution of a co-worker.
     */
    private static void runSolutionB(List<String> lines) {
        var bags = new HashMap<String, Bag>();
        for (var line : lines) {
            var parts = AocUtils.scan(line, "%s bag.*contain %s");
            var color = parts.get(0).get();
            var bag = bags.computeIfAbsent(color, Bag::new);
            var contents = parts.get(1).get();
            if (!contents.startsWith("no other")) {
                for (var content : contents.split(",")) {
                    var values = AocUtils.scan(content, " *%d %s bag.*");
                    var childColor = values.get(1).get();
                    var childBag = bags.computeIfAbsent(childColor, Bag::new);
                    bag.children.add(childBag);
                    bag.childrenCount.put(childColor, values.get(0).asInt());
                    childBag.parents.add(bag);
                }
            }
        }

        var s = "shiny gold";
        System.out.println("Puzzle 1: " + bags.get(s).collectAncestorColors().size());
        System.out.println("Puzzle 2: " + bags.get(s).countDescendants());
    }

    private static class Bag {

        final String color;
        final List<Bag> parents = new ArrayList<>();
        final List<Bag> children = new ArrayList<>();
        final Map<String, Integer> childrenCount = new HashMap<>();

        Bag(String color) {
            this.color = color;
        }

        Set<String> collectAncestorColors() {
            var result = new HashSet<String>();
            parents.forEach(b -> {
                result.add(b.color);
                result.addAll(b.collectAncestorColors());
            });
            return result;
        }

        long countDescendants() {
            return children.stream().mapToLong(c -> childrenCount.get(c.color) * (1 + c.countDescendants())).sum();
        }

    }

}
