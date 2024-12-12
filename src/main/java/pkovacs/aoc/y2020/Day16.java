package pkovacs.aoc.y2020;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import pkovacs.aoc.util.InputUtils;

public class Day16 {

    public static void main(String[] args) {
        var lines = InputUtils.readLineBlocks("day16.txt");

        // Read rules
        var rules = new HashMap<String, int[]>();
        for (var line : lines.get(0)) {
            var parts = line.split(":");
            rules.put(parts[0], InputUtils.scanInts(parts[1]));
        }
        var ruleCount = rules.keySet().size();

        // Trick: replace "nearby tickets:" with my ticket's row (assuming it's valid)
        lines.get(2)[0] = lines.get(1)[1];

        // Process tickets
        long errorRate = 0;
        SetMultimap<String, Integer> invalid = MultimapBuilder.hashKeys().hashSetValues().build();
        for (var line : lines.get(2)) {
            int[] values = InputUtils.scanInts(line);

            // Check validity
            int err = Arrays.stream(values).filter(v -> !isValid(v, rules)).sum();
            if (err > 0) {
                errorRate += err;
                continue;
            }

            // Collect the invalid positions for rules
            for (int i = 0; i < values.length; i++) {
                for (var k : rules.keySet()) {
                    if (!isValid(values[i], rules.get(k))) {
                        invalid.put(k, i);
                    }
                }
            }
        }

        // Collect potentially valid indices
        SetMultimap<String, Integer> canBeValid = MultimapBuilder.hashKeys().hashSetValues().build();
        for (var k : rules.keySet()) {
            IntStream.range(0, ruleCount)
                    .filter(i -> !invalid.containsEntry(k, i))
                    .forEach(i -> canBeValid.put(k, i));
        }

        // Assign rules to indices with greedy method
        var ruleIndex = new HashMap<String, Integer>();
        while (!canBeValid.isEmpty()) {
            var next = canBeValid.keySet().stream()
                    .filter(k -> canBeValid.get(k).size() == 1).findAny();
            if (next.isEmpty()) {
                throw new RuntimeException("Greedy assignment failed.");
            }

            int i = canBeValid.get(next.get()).iterator().next();
            ruleIndex.put(next.get(), i);
            canBeValid.entries().removeIf(e -> e.getValue() == i);
        }

        System.out.println("Part 1: " + errorRate);
        System.out.println("Part 2: " + getScoreForPart2(lines.get(1)[1], ruleIndex));
    }

    private static boolean isValid(long value, Map<String, int[]> rules) {
        return rules.values().stream().anyMatch(rule -> isValid(value, rule));
    }

    private static boolean isValid(long value, int[] rule) {
        return (value >= rule[0] && value <= rule[1]) || (value >= rule[2] && value <= rule[3]);
    }

    private static long getScoreForPart2(String ticket, Map<String, Integer> ruleIndex) {
        int[] values = InputUtils.scanInts(ticket);
        long mul = 1;
        for (var n : ruleIndex.keySet()) {
            if (n.startsWith("departure")) {
                mul *= values[ruleIndex.get(n)];
            }
        }
        return mul;
    }

}
