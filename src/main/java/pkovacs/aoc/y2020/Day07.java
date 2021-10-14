package pkovacs.aoc.y2020;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import pkovacs.aoc.util.InputUtils;

public class Day07 {

    public static void main(String[] args) {
        List<String> lines = InputUtils.readLines("y2020/day07.txt");

        ListMultimap<String, String> tree = MultimapBuilder.hashKeys().arrayListValues().build();
        SetMultimap<String, String> revTree = MultimapBuilder.hashKeys().hashSetValues().build();
        for (var line : lines) {
            var parts = InputUtils.scan(line, "%s bag.*contain %s");
            var bag = parts.get(0).get();
            var contents = parts.get(1).get();
            if (contents.startsWith("no other")) {
                continue;
            }
            for (var content : contents.split(",")) {
                var values = InputUtils.scan(content, " *%d %s bag.*");
                var innerBag = values.get(1).get();
                for (int i = 0, cnt = values.get(0).asInt(); i < cnt; i++) {
                    tree.put(bag, innerBag);
                    revTree.put(innerBag, bag);
                }
            }
        }

        var s = "shiny gold";
        System.out.println("Part 1: " + collectDescendantColors(revTree, s).size());
        System.out.println("Part 2: " + countDescendants(tree, s));
    }

    private static Set<String> collectDescendantColors(Multimap<String, String> tree, String node) {
        return tree.get(node).stream()
                .flatMap(n -> Stream.concat(Stream.of(n), collectDescendantColors(tree, n).stream()))
                .collect(Collectors.toSet());
    }

    private static long countDescendants(Multimap<String, String> tree, String node) {
        return tree.get(node).stream().mapToLong(n -> 1 + countDescendants(tree, n)).sum();
    }

}
