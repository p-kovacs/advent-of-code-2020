package pkovacs.aoc;

import java.util.HashMap;

import pkovacs.aoc.util.AocUtils;

public class Day06 {

    public static void main(String[] args) {
        var blocks = AocUtils.readLineBlocks("day06.txt");

        long sum1 = 0;
        long sum2 = 0;
        for (var block : blocks) {
            var map = new HashMap<Byte, Integer>();
            for (String line : block) {
                for (byte b : line.getBytes()) {
                    map.put(b, map.computeIfAbsent(b, x -> 0) + 1);
                }
            }
            sum1 += map.size();
            sum2 += map.values().stream().filter(cnt -> cnt == block.length).count();
        }

        System.out.println("Puzzle 1: " + sum1);
        System.out.println("Puzzle 2: " + sum2);
    }

}
