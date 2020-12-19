package pkovacs.aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import pkovacs.aoc.util.InputUtils;

public class Day19 {

    public static void main(String[] args) {
        var blocks = InputUtils.readLineBlocks("day19.txt");

        // Read rules, convert them to RegEx (spaces are preserved yet)
        var rules = new HashMap<String, String>();
        for (var line : blocks.get(0)) {
            var p = line.split(": ");
            var rule = p[1];
            if (rule.contains("|")) {
                rules.put(p[0], "(" + rule + ")");
            } else {
                rules.put(p[0], rule.replace("\"", ""));
            }
        }

        // Add special rule "0+" for solving part 2.
        // Rule 8 can be transformed to "(42)+", but rule 11 describes a type 2 grammar, it cannot be
        // represented with a RegEx, so we iterate the "infinite loop" up to a certain limit.
        // maxLineLength / 2 is a sufficient limit, because every rule eventually describes at least
        // one character.
        int limit = Arrays.stream(blocks.get(1)).mapToInt(String::length).max().getAsInt() / 2;
        rules.put("0+", "(42)+ 42 31");
        for (int i = 2; i <= limit; i++) {
            rules.put("0+", rules.get("0+") + " | (42)+" + " 42".repeat(i) + " 31".repeat(i));
        }

        // Iteratively replace numbers with corresponding rule strings within the two main rule strings
        var mainRules = new String[] { "0", "0+" };
        var pattern = Pattern.compile("([0-9]+)");
        boolean stable = false;
        while (!stable) {
            stable = true;
            for (var key : mainRules) {
                var rule = rules.get(key);
                var m = pattern.matcher(rule);
                var sb = new StringBuilder();
                while (m.find()) {
                    m.appendReplacement(sb, rules.get(m.group(1)));
                }
                m.appendTail(sb);

                var newRule = sb.toString();
                if (!newRule.equals(rule)) {
                    rules.put(key, newRule);
                    stable = false;
                }
            }
        }

        System.out.println("Part 1: " + countMatches(rules.get("0"), blocks.get(1)));
        System.out.println("Part 2: " + countMatches(rules.get("0+"), blocks.get(1)));
    }

    private static long countMatches(String rule, String[] lines) {
        // Remove spaces from the rule, simplify trivial parts, and build pattern
        var pattern = Pattern.compile(rule
                .replace(" ", "")
                .replace("(a|b)", "[ab]")
                .replace("(b|a)", "[ab]"));

        // Count matching lines
        return Arrays.stream(lines).filter(line -> pattern.matcher(line).matches()).count();
    }

}
