package pkovacs.aoc;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class Day02 {

    public static void main(String[] args) {
        Scanner in = new Scanner(Day02.class.getResourceAsStream("day02.txt"));
        in.useDelimiter("[ ,:\\n-]");

        int cnt1 = 0;
        int cnt2 = 0;
        while (in.hasNext()) {
            int x = in.nextInt();
            int y = in.nextInt();
            char c = in.next().charAt(0);
            in.next();
            var pwd = in.next();
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
