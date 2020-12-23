package pkovacs.aoc;

import java.util.ArrayList;
import java.util.List;

import com.google.common.primitives.Ints;
import pkovacs.aoc.util.InputUtils;

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

        if (advanced) {
            return String.valueOf((long) list.get(0) * list.get(1));
        } else {
            return list.stream().map(String::valueOf).collect(joining(""));
        }
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

        // Build data structures. (Each value remains at the same position in values array, but their order is
        // stored as a linked list using the next array, and a reverse lookup map is also stored in index array.)
        int[] values = Ints.toArray(list);
        int[] next = new int[n];
        int[] index = new int[n + 1];
        for (int i = 0; i < n; i++) {
            next[i] = i == n - 1 ? 0 : i + 1;
            index[values[i]] = i;
        }

        // Play the moves
        for (int move = 0, current = 0; move < moveCount; move++, current = next[current]) {
            // Select indices to pick up
            int pu1 = next[current];
            int pu2 = next[pu1];
            int pu3 = next[pu2];

            // Select destination value an index
            int currentValue = values[current];
            int dest = currentValue;
            while (dest == currentValue || dest == values[pu1] || dest == values[pu2] || dest == values[pu3]) {
                if (--dest < 1) {
                    dest = max;
                }
            }
            int destIndex = index[dest];

            // Update list
            next[current] = next[pu3];
            next[pu3] = next[destIndex];
            next[destIndex] = pu1;
        }

        // Construct result list: the first few values next to 1
        List<Integer> result = new ArrayList<>();
        for (int i = next[index[1]]; result.size() < input.size() - 1; i = next[i]) {
            result.add(values[i]);
        }
        return result;
    }

}
