package pkovacs.aoc.alg;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import pkovacs.aoc.util.Cell;
import pkovacs.aoc.util.Pair;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortestPathTest {

    @Test
    public void findPathInMaze() throws Exception {
        // We have to find the shortest path in a maze from the top left tile to the bottom right tile.
        // Walls should be bypassed or "blown up", but it takes detonationTime seconds to blow up a single wall
        // tile next to the current tile and step into its location, while a single step to an adjacent empty
        // tile takes only 1 second.
        // See maze2.txt, '#' represents a wall tile, '.' represents an empty tile.

        var maze = Files.readAllLines(Path.of(getClass().getResource("maze2.txt").toURI()));
        var start = new Cell(0, 0);
        var end = new Cell(9, 11);

        // Find path with large detonationTime --> same as BFS
        long detonationTime = 32;
        var resultBfs = findPathInMazeBfs(maze, start, end);
        var result = findPathInMazeSp(maze, start, end, detonationTime);

        assertEquals(50, resultBfs.getDist());
        assertEquals(50, result.getDist());
        assertEquals(resultBfs.getPath(), result.getPath());

        // Find path with smaller detonationTime --> better than BFS
        detonationTime = 30;
        result = findPathInMazeSp(maze, start, end, detonationTime);

        assertEquals(49, result.getDist());

        // Find path with detonationTime == 1 --> Manhattan distance
        detonationTime = 1;
        result = findPathInMazeSp(maze, start, end, detonationTime);

        assertEquals(20, result.getDist());
        assertEquals(start.getManhattanDistance(end), result.getDist());
    }

    private static PathResult<Cell> findPathInMazeBfs(List<String> maze, Cell start, Cell end) {
        var result = Bfs.run(start,
                pos -> pos.getFourNeighbors().stream()
                        .filter(p -> p.isValid(maze.size(), maze.get(0).length()))
                        .filter(p -> maze.get(p.row).charAt(p.col) == '.')
                        .collect(toList()),
                end::equals);

        assertTrue(result.isPresent());
        return result.get();
    }

    private static PathResult<Cell> findPathInMazeSp(List<String> maze, Cell start,
            Cell end, long detonationTime) {
        var result = ShortestPath.find(start,
                pos -> pos.getFourNeighbors().stream()
                        .filter(p -> p.isValid(maze.size(), maze.get(0).length()))
                        .map(p -> new Pair<>(p, maze.get(p.row).charAt(p.col) == '.' ? 1 : detonationTime))
                        .collect(toList()),
                end::equals);

        assertTrue(result.isPresent());
        return result.get();
    }

}
