package pkovacs.aoc.y2020;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.IntStream;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.math.IntMath;
import com.google.common.primitives.Chars;
import pkovacs.aoc.util.InputUtils;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Day20 {

    private static final char[][] SEA_MONSTER = new char[][] {
            "                  # ".toCharArray(),
            "#    ##    ##    ###".toCharArray(),
            " #  #  #  #  #  #   ".toCharArray(),
    };

    public static void main(String[] args) {
        var blocks = InputUtils.readLineBlocks("day20.txt");

        // Read input, generate all transformed tiles
        int n = 0;
        ListMultimap<Integer, char[][]> tiles = MultimapBuilder.hashKeys().arrayListValues().build();
        for (var block : blocks) {
            var id = Integer.parseInt(block[0].split(" ")[1].split(":")[0]);
            n = block.length - 1;
            var m = new char[n][];
            for (int i = 0; i < n; i++) {
                m[i] = new char[block[i + 1].length()];
                for (int j = 0; j < n; j++) {
                    m[i][j] = block[i + 1].charAt(j);
                }
            }
            tiles.putAll(id, getAllTransformedTiles(m));
        }

        // Calculate inner edge count of each tile
        var innerEdgeCounts = tiles.keySet().stream()
                .collect(toMap(id -> id, id -> getInnerEdgeCount(tiles, id)));

        // Fill the grid of tiles using a greedy approach
        int s = IntMath.sqrt(tiles.keySet().size(), RoundingMode.DOWN);
        var grid = new char[s][s][][];
        var freeTiles = new HashSet<>(tiles.keySet());
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                int innerEdgeCount = 4 - (i == 0 || i == s - 1 ? 1 : 0) - (j == 0 || j == s - 1 ? 1 : 0);
                // Find the appropriate tile and transformation for cell (i,j)
                for (int id : new ArrayList<>(freeTiles)) {
                    if (innerEdgeCounts.get(id) != innerEdgeCount) {
                        continue;
                    }
                    var ft = findFeasibleTransformation(tiles, grid, i, j, id);
                    if (ft.isPresent()) {
                        grid[i][j] = ft.get();
                        freeTiles.remove(id);
                        break;
                    }
                }
            }
        }

        // Construct the full image
        int k = n - 2;
        int nn = s * k;
        char[][] image = new char[nn][nn];
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                var m = grid[i][j];
                for (int a = 0; a < k; a++) {
                    for (int b = 0; b < k; b++) {
                        image[i * k + a][j * k + b] = m[a + 1][b + 1];
                    }
                }
            }
        }

        // Calculate solution for part 1
        long solution1 = innerEdgeCounts.entrySet().stream()
                .filter(e -> e.getValue() == 2)
                .mapToLong(Entry::getKey)
                .reduce(1L, (a, b) -> a * b);

        // Calculate solution for part 2
        int solution2 = getAllTransformedTiles(image).stream()
                .mapToInt(Day20::getSeaMonsterCount)
                .min().getAsInt();

        System.out.println("Part 1: " + solution1);
        System.out.println("Part 2: " + solution2);
    }

    private static List<char[][]> getAllTransformedTiles(char[][] matrix) {
        return IntStream.range(0, 8).mapToObj(code -> getTransformedTile(matrix, code)).collect(toList());
    }

    private static char[][] getTransformedTile(char[][] matrix, int code) {
        boolean flip1 = (code & 4) != 0;
        boolean flip2 = (code & 2) != 0;
        boolean rotate = (code & 1) != 0;
        int n = matrix.length;
        char[][] newMatrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char ch = matrix[flip1 ? n - 1 - i : i][flip2 ? n - 1 - j : j];
                if (rotate) {
                    newMatrix[i][j] = ch;
                } else {
                    newMatrix[j][i] = ch;
                }
            }
        }
        return newMatrix;
    }

    private static boolean matchVertically(char[][] m1, char[][] m2) {
        return IntStream.range(0, m1.length).allMatch(i -> m1[m1.length - 1][i] == m2[0][i]);
    }

    private static boolean matchHorizontally(char[][] m1, char[][] m2) {
        return IntStream.range(0, m1.length).allMatch(i -> m1[i][m1.length - 1] == m2[i][0]);
    }

    private static int getInnerEdgeCount(ListMultimap<Integer, char[][]> allTiles, int id) {
        return getInnerEdgeCount(allTiles, id, allTiles.get(id).get(0), true, true, true, true);
    }

    private static int getInnerEdgeCount(ListMultimap<Integer, char[][]> allTiles, int id, char[][] m,
            boolean t, boolean r, boolean b, boolean l) {
        int cnt = 0;
        for (int id2 : allTiles.keySet()) {
            if (id2 == id) {
                continue;
            }
            for (var m2 : allTiles.get(id2)) {
                if ((t && matchVertically(m2, m))
                        || (b && matchVertically(m, m2))
                        || (l && matchHorizontally(m2, m))
                        || (r && matchHorizontally(m, m2))) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private static Optional<char[][]> findFeasibleTransformation(ListMultimap<Integer, char[][]> allTiles,
            char[][][][] grid, int i, int j, int id) {
        for (var m : allTiles.get(id)) {
            if (i > 0) {
                // Check: match with the neighbor above
                char[][] m2 = grid[i - 1][j];
                if (!matchVertically(m2, m)) {
                    continue;
                }
            } else {
                // Check: no match above
                if (getInnerEdgeCount(allTiles, id, m, true, false, false, false) != 0) {
                    continue;
                }
            }
            if (j > 0) {
                // Check: match with the neighbor to the left
                char[][] m2 = grid[i][j - 1];
                if (!matchHorizontally(m2, m)) {
                    continue;
                }
            } else {
                if (getInnerEdgeCount(allTiles, id, m, false, false, false, true) != 0) {
                    continue;
                }
            }
            return Optional.of(m);
        }
        return Optional.empty();
    }

    private static int getSeaMonsterCount(char[][] fm) {
        for (int i = 0; i < fm.length - SEA_MONSTER.length; i++) {
            for (int j = 0; j < fm.length - SEA_MONSTER[0].length; j++) {
                if (isSeaMonsterFound(fm, i, j)) {
                    markSeaMonster(fm, i, j);
                }
            }
        }

        return (int) Arrays.stream(fm)
                .flatMap(line -> Chars.asList(line).stream())
                .filter(c -> c == '#')
                .count();
    }

    private static boolean isSeaMonsterFound(char[][] fm, int ii, int jj) {
        for (int a = 0; a < SEA_MONSTER.length; a++) {
            for (int b = 0; b < SEA_MONSTER[0].length; b++) {
                if (SEA_MONSTER[a][b] == '#' && fm[ii + a][jj + b] != SEA_MONSTER[a][b]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void markSeaMonster(char[][] fm, int i, int j) {
        for (int a = 0; a < SEA_MONSTER.length; a++) {
            for (int b = 0; b < SEA_MONSTER[0].length; b++) {
                if (SEA_MONSTER[a][b] == '#') {
                    fm[i + a][j + b] = 'O';
                }
            }
        }
    }

}
