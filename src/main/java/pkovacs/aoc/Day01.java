package pkovacs.aoc;

import java.util.ArrayList;
import java.util.Scanner;

import pkovacs.aoc.util.AocUtils;

public class Day01 {

    public static void main(String[] args) {
        Scanner in = AocUtils.scanFile("day01.txt");
        var list = new ArrayList<Integer>();
        while (in.hasNext()) {
            list.add(in.nextInt());
        }

        int solution1 = 0;
        int solution2 = 0;
        for (int i = 0; i < list.size(); i++) {
            int x = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                int y = list.get(j);
                if (x + y == 2020) {
                    solution1 = x * y;
                }
                for (int k = j + 1; k < list.size(); k++) {
                    int z = list.get(k);
                    if (x + y + z == 2020) {
                        solution2 = x * y * z;
                    }
                }
            }
        }

        System.out.println("Puzzle 1: " + solution1);
        System.out.println("Puzzle 2: " + solution2);
    }

}
