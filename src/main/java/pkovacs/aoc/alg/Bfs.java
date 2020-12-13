package pkovacs.aoc.alg;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A general implementation of the BFS (breadth-first search) algorithm.
 * <p>
 * The input is a directed graph (defined by a neighbor provider function) and one or more source nodes.
 * An optional target predicate can also be specified in order to get path result for a single node
 * instead of all nodes. In this case, the algorithm terminates when the shortest path is found for at least
 * one target node (more precisely, when all target nodes having minimum distance are found).
 */
public final class Bfs {

    private Bfs() {
    }

    public static <T> Map<T, PathResult<T>> run(T source, Function<T, Iterable<T>> neighborProvider) {
        return run(Collections.singleton(source), neighborProvider, t -> false);
    }

    public static <T> Optional<PathResult<T>> run(T source, Function<T, Iterable<T>> neighborProvider,
            Predicate<T> targetPredicate) {
        var map = run(Collections.singleton(source), neighborProvider, targetPredicate);
        return map.values().stream().filter(PathResult::isTarget).findFirst();
    }

    public static <T> Map<T, PathResult<T>> run(Iterable<T> sources, Function<T, Iterable<T>> neighborProvider,
            Predicate<T> targetPredicate) {
        var results = new HashMap<T, PathResult<T>>();

        var queue = new ArrayDeque<T>();
        for (T source : sources) {
            boolean target = targetPredicate.test(source);
            results.put(source, new PathResult<>(source, 0, target, null));
            queue.add(source);
        }

        while (!queue.isEmpty()) {
            T node = queue.poll();
            var front = results.get(node);
            if (front.isTarget()) {
                break;
            }
            for (T neighbor : neighborProvider.apply(node)) {
                if (!results.containsKey(neighbor)) {
                    results.put(neighbor, new PathResult<>(neighbor, front.getDist() + 1,
                            targetPredicate.test(neighbor), front));
                    queue.add(neighbor);
                }
            }
        }

        return results;
    }

}
