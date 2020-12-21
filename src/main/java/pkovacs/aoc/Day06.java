package pkovacs.aoc;

import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.LongMap;

public class Day06 {

    public static void main(String[] args) {
        var blocks = InputUtils.readLineBlocks("day06.txt");

        long sum1 = 0;
        long sum2 = 0;
        for (var block : blocks) {
            var map = new LongMap<Byte>();
            for (String line : block) {
                for (byte b : line.getBytes()) {
                    map.incrementAndGet(b);
                }
            }
            sum1 += map.size();
            sum2 += map.count(block.length);
        }

        System.out.println("Part 1: " + sum1);
        System.out.println("Part 2: " + sum2);
    }

}
