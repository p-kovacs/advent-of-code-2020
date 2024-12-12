package pkovacs.aoc.y2020;

import pkovacs.aoc.util.CounterMap;
import pkovacs.aoc.util.InputUtils;

public class Day06 {

    public static void main(String[] args) {
        var blocks = InputUtils.readLineBlocks("day06.txt");

        long sum1 = 0;
        long sum2 = 0;
        for (var block : blocks) {
            var map = new CounterMap<Byte>();
            for (String line : block) {
                for (byte b : line.getBytes()) {
                    map.inc(b);
                }
            }
            sum1 += map.size();
            sum2 += map.count(block.length);
        }

        System.out.println("Part 1: " + sum1);
        System.out.println("Part 2: " + sum2);
    }

}
