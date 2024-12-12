package pkovacs.aoc.y2020;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.IntPair;

import static java.util.stream.Collectors.toList;

public class Day22 {

    public static void main(String[] args) {
        var blocks = InputUtils.readLineBlocks("day22.txt");

        var deck1 = IntStream.range(1, blocks.get(0).length)
                .mapToObj(i -> Integer.parseInt(blocks.get(0)[i]))
                .collect(toList());
        var deck2 = IntStream.range(1, blocks.get(1).length)
                .mapToObj(i -> Integer.parseInt(blocks.get(1)[i]))
                .collect(toList());

        System.out.println("Part 1: " + solve(deck1, deck2, false).b());
        System.out.println("Part 2: " + solve(deck1, deck2, true).b());
    }

    public static IntPair solve(List<Integer> d1, List<Integer> d2, boolean recursive) {
        var deck1 = new ArrayDeque<>(d1);
        var deck2 = new ArrayDeque<>(d2);

        var history1 = new HashSet<List<Integer>>();
        var history2 = new HashSet<List<Integer>>();
        while (!deck1.isEmpty() && !deck2.isEmpty()) {
            if (recursive && (!history1.add(List.copyOf(deck1)) || !history2.add(List.copyOf(deck2)))) {
                deck2.clear();
                break;
            }

            var top1 = deck1.removeFirst();
            var top2 = deck2.removeFirst();

            boolean p1wins = top1 > top2;
            if (recursive && top1 <= deck1.size() && top2 <= deck2.size()) {
                var pair = solve(List.copyOf(deck1).subList(0, top1),
                        List.copyOf(deck2).subList(0, top2), true);
                p1wins = pair.a() == 1;
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
