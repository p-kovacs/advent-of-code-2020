package pkovacs.aoc;

import pkovacs.aoc.util.InputUtils;

public class Day02 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day02.txt");

        int cnt1 = 0;
        int cnt2 = 0;
        for (String line : lines) {
            var values = InputUtils.scan(line, "%d-%d %c: %s");
            int a = values.get(0).asInt();
            int b = values.get(1).asInt();
            char ch = values.get(2).asChar();
            var pwd = values.get(3).get();
            long m = pwd.chars().filter(c -> c == ch).count();
            if (m >= a && m <= b) {
                cnt1++;
            }
            if ((pwd.charAt(a - 1) == ch) ^ (pwd.charAt(b - 1) == ch)) {
                cnt2++;
            }
        }

        System.out.println("Part 1: " + cnt1);
        System.out.println("Part 2: " + cnt2);
    }

}
