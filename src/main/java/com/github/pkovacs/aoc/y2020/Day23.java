package com.github.pkovacs.aoc.y2020;

import java.util.ArrayList;
import java.util.List;

import com.github.pkovacs.aoc.util.InputUtils;

import static java.util.stream.Collectors.joining;

public class Day23 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day23.txt");

        var list = new ArrayList<Integer>();
        for (int i = 0; i < lines.get(0).length(); i++) {
            list.add(Integer.parseInt(lines.get(0).substring(i, i + 1)));
        }

        System.out.println("Part 1: " + solve(list, false));
        System.out.println("Part 2: " + solve(list, true));
    }

    private static String solve(List<Integer> input, boolean advanced) {
        List<Integer> list = play(input, advanced);

        return advanced
                ? String.valueOf((long) list.get(0) * list.get(1))
                : list.stream().map(String::valueOf).collect(joining(""));
    }

    private static List<Integer> play(List<Integer> input, boolean advanced) {
        int moveCount = advanced ? 10_000_000 : 100;
        int max = input.stream().mapToInt(a -> a).max().getAsInt();

        // Extend list for part 2
        var list = new ArrayList<>(input);
        int n = advanced ? 1_000_000 : list.size();
        if (advanced) {
            for (int k = max + 1; k <= 1_000_000; k++) {
                list.add(k);
            }
            max = 1_000_000;
        }

        // Build linked list from the values
        int[] next = new int[n + 1];
        for (int i = 0; i < n; i++) {
            next[list.get(i)] = list.get(i == n - 1 ? 0 : i + 1);
        }

        // Play the moves
        for (int move = 0, current = list.get(0); move < moveCount; move++, current = next[current]) {
            int pu1 = next[current];
            int pu2 = next[pu1];
            int pu3 = next[pu2];

            int dest = current;
            while (dest == current || dest == pu1 || dest == pu2 || dest == pu3) {
                if (--dest < 1) {
                    dest = max;
                }
            }

            next[current] = next[pu3];
            next[pu3] = next[dest];
            next[dest] = pu1;
        }

        // Construct result list: the first few values next to 1
        List<Integer> result = new ArrayList<>();
        for (int i = next[1]; result.size() < input.size() - 1; i = next[i]) {
            result.add(i);
        }
        return result;
    }

}
