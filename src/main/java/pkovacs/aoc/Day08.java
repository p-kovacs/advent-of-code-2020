package pkovacs.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pkovacs.aoc.util.AocUtils;

public class Day08 {

    public static void main(String[] args) {
        List<String> lines = AocUtils.readLines("day08.txt");

        System.out.println("Puzzle 1: " + runCode(lines, true).get());
        System.out.println("Puzzle 2: " + solvePuzzle2(lines));
    }

    private static long solvePuzzle2(List<String> origLines) {
        for (int i = 0; i < origLines.size(); i++) {
            if (origLines.get(i).startsWith("acc")) {
                continue;
            }
            var lines = new ArrayList<>(origLines);
            lines.set(i, lines.get(i).startsWith("jmp")
                    ? lines.get(i).replace("jmp", "nop")
                    : lines.get(i).replace("nop", "jmp"));

            var res = runCode(lines, false);
            if (res.isPresent()) {
                return res.get();
            }
        }
        return 0; // should not occur
    }

    private static Optional<Long> runCode(List<String> lines, boolean infiniteAllowed) {
        long acc = 0;
        int pos = 0;
        boolean[] executed = new boolean[lines.size()];
        while (pos >= 0 && pos < lines.size()) {
            if (executed[pos]) {
                return infiniteAllowed ? Optional.of(acc) : Optional.empty();
            }
            executed[pos] = true;
            var p = lines.get(pos).split(" ");
            var cmd = p[0];
            var v = Integer.parseInt(p[1]);
            if ("nop".equals(cmd)) {
                pos++;
            } else if ("acc".equals(cmd)) {
                acc += v;
                pos++;
            } else if ("jmp".equals(cmd)) {
                pos += v;
            }
        }
        return Optional.of(acc);
    }

}
