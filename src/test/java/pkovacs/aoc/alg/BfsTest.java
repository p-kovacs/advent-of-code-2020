package pkovacs.aoc.alg;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import pkovacs.aoc.util.IntPair;
import pkovacs.aoc.util.Tile;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BfsTest {

    @Test
    public void testJugs() {
        // Inspired by Die Hard 3... :)
        // We have a 3-liter jug, a 5-liter jug, and a fountain. Let's measure 4 liters of water.
        // BFS algorithm can be used for finding the optimal path in an "implicit graph": the nodes represent
        // valid states, and the edges represent state transformations (steps). The graph is not generated
        // explicitly, but the next states are generated on-the-fly during the traversal.

        var result = Bfs.run(new IntPair(0, 0), state -> {
            var list = new ArrayList<IntPair>();
            list.add(new IntPair(3, state.b())); // 3-liter jug <-- fountain
            list.add(new IntPair(state.a(), 5)); // 5-liter jug <-- fountain
            list.add(new IntPair(0, state.b())); // 3-liter jug --> fountain
            list.add(new IntPair(state.a(), 0)); // 5-liter jug --> fountain
            int d1 = Math.min(3 - state.a(), state.b());
            list.add(new IntPair(state.a() + d1, state.b() - d1)); // 3-liter jug <-- 5-liter jug
            int d2 = Math.min(5 - state.b(), state.a());
            list.add(new IntPair(state.a() - d2, state.b() + d2)); // 3-liter jug --> 5-liter jug
            return list;
        }, pair -> pair.b() == 4);

        assertTrue(result.isPresent());
        assertEquals(6, result.get().getDist());
        assertEquals(List.of(new IntPair(0, 0),
                new IntPair(0, 5),
                new IntPair(3, 2),
                new IntPair(0, 2),
                new IntPair(2, 0),
                new IntPair(2, 5),
                new IntPair(3, 4)),
                result.get().getPath());
    }

    @Test
    public void testMaze() throws Exception {
        // We have to find the shortest path in a maze from the top left tile to the bottom right tile.
        // See maze1.txt, '#' represents a wall tile, '.' represents an empty tile.

        var maze = Files.readAllLines(Path.of(getClass().getResource("maze1.txt").toURI()));
        var start = new Tile(0, 0);
        var end = new Tile(9, 11);

        var result = Bfs.run(start,
                cell -> cell.getFourNeighbors().stream()
                        .filter(c -> c.isValid(maze.size(), maze.get(0).length()))
                        .filter(c -> maze.get(c.row).charAt(c.col) == '.')
                        .collect(toList()),
                end::equals);

        assertTrue(result.isPresent());
        assertEquals(32, result.get().getDist());
        assertEquals(end, result.get().getNode());

        var path = result.get().getPath();
        assertEquals(33, path.size());
        assertEquals(start, path.get(0));
        assertEquals(end, path.get(path.size() - 1));
    }

    @Test
    public void testWithInfiniteGraph() {
        var result1 = Bfs.run(0, i -> List.of(i + 1, 2 * i), i -> i == 128);
        var result2 = Bfs.run(0, i -> List.of(i + 1, 2 * i), i -> i == 127);
        var result3 = Bfs.run(0, i -> List.of(i + 1, 2 * i), i -> i == 42);
        var result4 = Bfs.run(0, i -> List.of(i + 1, 2 * i), i -> i == 137);

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertTrue(result3.isPresent());
        assertTrue(result4.isPresent());
        assertEquals(List.of(0, 1, 2, 4, 8, 16, 32, 64, 128), result1.get().getPath());
        assertEquals(List.of(0, 1, 2, 3, 6, 7, 14, 15, 30, 31, 62, 63, 126, 127), result2.get().getPath());
        assertEquals(List.of(0, 1, 2, 4, 5, 10, 20, 21, 42), result3.get().getPath());
        assertEquals(List.of(0, 1, 2, 4, 8, 16, 17, 34, 68, 136, 137), result4.get().getPath());
    }

}
