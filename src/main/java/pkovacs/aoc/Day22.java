package pkovacs.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.IntPair;

public class Day22 {

    public static void main(String[] args) {
        var blocks = InputUtils.readLineBlocks("day22.txt");

        var deck1 = new ArrayList<Integer>();
        var deck2 = new ArrayList<Integer>();
        IntStream.range(1, blocks.get(0).length)
                .forEachOrdered(i -> deck1.add(Integer.parseInt(blocks.get(0)[i])));
        IntStream.range(1, blocks.get(1).length)
                .forEachOrdered(i -> deck2.add(Integer.parseInt(blocks.get(1)[i])));

        System.out.println("Part 1: " + solve(deck1, deck2, false).b);
        System.out.println("Part 2: " + solve(deck1, deck2, true).b);
        //Part 1:
        //Part 2:
    }

    public static IntPair solve(List<Integer> d1, List<Integer> d2, boolean recursive) {
        var deck1 = new ArrayDeque<>(d1);
        var deck2 = new ArrayDeque<>(d2);

        var history1 = new HashSet<List<Integer>>();
        var history2 = new HashSet<List<Integer>>();
        while (!deck1.isEmpty() && !deck2.isEmpty()) {
            if (history1.contains(new ArrayList<>(deck1)) && history2.contains(new ArrayList<>(deck2))) {
                deck2.clear();
                break;
            } else {
                history1.add(new ArrayList<>(deck1));
                history2.add(new ArrayList<>(deck2));
            }

            var top1 = deck1.removeFirst();
            var top2 = deck2.removeFirst();

            boolean p1wins = top1 > top2;

            if (recursive && top1 <= deck1.size() && top2 <= deck2.size()) {
                var pair = solve(new ArrayList<>(deck1).subList(0, top1),
                        new ArrayList<>(deck2).subList(0, top2), true);
                p1wins = pair.a == 1;
            }

            if (p1wins) {
                deck1.addLast(top1);
                deck1.addLast(top2);
            } else {
                deck2.addLast(top2);
                deck2.addLast(top1);
            }
        }

        var winner = deck1.isEmpty() ? 2 : 1;
        var winnerDeck = new ArrayList<>(deck1.isEmpty() ? deck2 : deck1);
        var score = IntStream.range(0, winnerDeck.size())
                .map(i -> winnerDeck.get(i) * (winnerDeck.size() - i)).sum();
        return new IntPair(winner, score);
    }

}
