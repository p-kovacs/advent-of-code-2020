package pkovacs.aoc;

import pkovacs.aoc.util.InputUtils;

public class Day25 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day25.txt");

        long pk1 = Long.parseLong(lines.get(0));
        long pk2 = Long.parseLong(lines.get(1));

        System.out.println("Part 1: " + encrypt(pk2, findLoopSize(pk1)));
        System.out.println("Part 2: ");
    }

    private static int findLoopSize(long key) {
        long subjectNumber = 7;
        int c = 0;
        for (long x = 1; x != key; x = (x * subjectNumber) % 20201227) {
            c++;
        }
        return c;
    }

    private static long encrypt(long subjectNumber, int loopSize) {
        long x = 1;
        for (int i = 0; i < loopSize; i++) {
            x = (x * subjectNumber) % 20201227;
        }
        return x;
    }

}
