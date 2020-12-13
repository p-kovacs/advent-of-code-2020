package pkovacs.aoc;

import org.apache.commons.lang3.StringUtils;
import pkovacs.aoc.util.InputUtils;

public class Day02 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day02.txt");

        int cnt1 = 0;
        int cnt2 = 0;
        for (String line : lines) {
            var values = InputUtils.scan(line, "%d-%d %c: %s");
            int x = values.get(0).asInt();
            int y = values.get(1).asInt();
            char c = values.get(2).asChar();
            var pwd = values.get(3).get();
            int m = StringUtils.countMatches(pwd, c);
            if (m >= x && m <= y) {
                cnt1++;
            }
            if ((pwd.charAt(x - 1) == c) ^ (pwd.charAt(y - 1) == c)) {
                cnt2++;
            }
        }

        System.out.println("Puzzle 1: " + cnt1);
        System.out.println("Puzzle 2: " + cnt2);
    }

}
