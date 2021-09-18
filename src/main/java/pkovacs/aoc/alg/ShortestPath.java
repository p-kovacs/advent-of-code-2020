package pkovacs.aoc.alg;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Implements a general shortest path algorithm, which is a more efficient version of the classic Bellman-Ford
 * algorithm. Namely, it is the
 * <a href="https://en.wikipedia.org/wiki/Shortest_Path_Faster_Algorithm">SPFA algorithm</a>.
 * <p>
 * The input is a directed graph with long integer edge weights (defined by an edge provide function) and one or more
 * source nodes. The edge provider function must provide a collection of (node, length) pairs for each node.
 * An optional target predicate can also be specified in order to get path result for a single node instead of
 * all nodes. However, it does not make the search process faster.
 * <p>
 * The algorithm also supports negative edge weights, but the graph must not contain a directed cycle with negative
 * total weight. The current implementation might not terminate for such input!
 */
public final class ShortestPath {

    private ShortestPath() {
    }

    public static <T> Map<T, PathResult<T>> find(T source,
            Function<T, Iterable<Pair<T, Long>>> edgeProvider) {
        return find(Collections.singleton(source), edgeProvider, n -> false);
    }

    public static <T> Optional<PathResult<T>> find(T source,
            Function<T, Iterable<Pair<T, Long>>> edgeProvider,
            Predicate<T> targetPredicate) {
        var map = find(Collections.singleton(source), edgeProvider, targetPredicate);
        return map.values().stream().filter(PathResult::isTarget).findFirst();
    }

    public static <T> Map<T, PathResult<T>> find(Iterable<T> sources,
            Function<T, Iterable<Pair<T, Long>>> edgeProvider,
            Predicate<T> targetPredicate) {
        var results = new HashMap<T, PathResult<T>>();

        var queue = new ArrayDeque<T>();
        for (T source : sources) {
            results.put(source, new PathResult<>(source, 0, targetPredicate.test(source), null));
            queue.add(source);
        }

        while (!queue.isEmpty()) {
            T node = queue.poll();
            var result = results.get(node);
            for (var edge : edgeProvider.apply(node)) {
                var neighbor = edge.getKey();
                var dist = result.getDist() + edge.getValue();
                var current = results.get(neighbor);
                if (current == null || dist < current.getDist()) {
                    results.put(neighbor, new PathResult<>(neighbor, dist, targetPredicate.test(neighbor), result));
                    queue.add(neighbor);
                }
            }
        }

        return results;
    }

}
