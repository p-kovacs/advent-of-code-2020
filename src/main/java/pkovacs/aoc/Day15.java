package pkovacs.aoc;

import java.util.Arrays;
import java.util.List;

import pkovacs.aoc.util.InputUtils;

public class Day15 {

    public static void main(String[] args) {
        List<String> lines = InputUtils.readLines("day15.txt");
        var numbers = Arrays.stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        System.out.println("Puzzle 1: " + solve(numbers, 2020));
        System.out.println("Puzzle 2: " + solve(numbers, 30000000));
    }

    private static long solve(int[] numbers, int stepCount) {
        // pos[i] is the index of the most recent occurrence of i (or -1 if not occurred yet) in the
        // number sequence (which is not stored explicitly, only its current element).
        // An array is used instead of a Map for efficiency reasons. Since every new number in the sequence
        // is either 0 or the difference of two indices, it is safe to use an array of size stepCount.
        var pos = new int[stepCount];
        Arrays.fill(pos, -1);
        for (int i = 0; i < numbers.length - 1; i++) {
            pos[numbers[i]] = i;
        }

        var current = numbers[numbers.length - 1];
        for (int i = numbers.length - 1; i < stepCount - 1; i++) {
            var index = pos[current];
            pos[current] = i;
            current = index < 0 ? 0 : i - index;
        }

        return current;
    }

}
