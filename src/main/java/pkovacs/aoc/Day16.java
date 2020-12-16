package pkovacs.aoc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        // Trick: replace "nearby tickets:" with my ticket's row (assuming it's valid)
        lines.get(2)[0] = lines.get(1)[1];

        // Check all tickets
        var invalidPos = new HashMap<String, Set<Integer>>();
        rules.keySet().forEach(rn -> invalidPos.put(rn, new HashSet<>()));
        long errorRate = 0;
        for (var line : lines.get(2)) {
            int[] values = InputUtils.scanInts(line);

            // Check validity
            boolean valid = true;
            for (long value : values) {
                if (!isValid(value, rules)) {
                    errorRate += value;
                    valid = false;
                }
            }
            if (!valid) {
                continue;
            }

            // Collect the invalid positions for rules
            for (int i = 0; i < values.length; i++) {
                for (var rn : rules.keySet()) {
                    if (!isValid(values[i], rules.get(rn))) {
                        invalidPos.get(rn).add(i);
                    }
                }
            }
        }

        // Assign rules to positions (assuming that it is unambiguous for each rule)
        var ruleCount = rules.keySet().size();
        var ruleIndex = new HashMap<String, Integer>();
        var toAssign = new HashSet<>(rules.keySet());
        while (!toAssign.isEmpty()) {
            for (var rn : toAssign) {
                var invalids = invalidPos.get(rn);
                if (invalids.size() == ruleCount - 1) {
                    int i = getAbsentValue(invalids, ruleCount);
                    ruleIndex.put(rn, i);
                    invalidPos.values().forEach(set -> set.add(i));
                }
            }
            if (!toAssign.removeAll(ruleIndex.keySet())) {
                throw new RuntimeException("Greedy assignment failed.");
            }
        }

        System.out.println("Puzzle 1: " + errorRate);
        System.out.println("Puzzle 2: " + getScoreForPart2(lines.get(1)[1], ruleIndex));
    }

    private static boolean isValid(long value, Map<String, int[]> rules) {
        return rules.values().stream().anyMatch(rule -> isValid(value, rule));
    }

    private static boolean isValid(long value, int[] rule) {
        return (value >= rule[0] && value <= rule[1]) || (value >= rule[2] && value <= rule[3]);
    }

    private static int getAbsentValue(Set<Integer> set, int maxValue) {
        for (int i = 0; i < maxValue; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        throw new IllegalArgumentException();
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
