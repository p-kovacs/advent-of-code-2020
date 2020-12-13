package pkovacs.aoc.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Result object for general path search algorithms: {@link Bfs} and {@link ShortestPath}.
 */
public class PathResult<T> {

    private final T node;
    private final long dist;
    private final boolean target;
    private final PathResult<T> prev;

    private List<T> path;

    PathResult(T node, long dist, boolean target, PathResult<T> prev) {
        this.node = node;
        this.dist = dist;
        this.target = target;
        this.prev = prev;
    }

    public T getNode() {
        return node;
    }

    public long getDist() {
        return dist;
    }

    public boolean isTarget() {
        return target;
    }

    public List<T> getPath() {
        if (path == null) {
            // Lazy load: construct path
            var list = new ArrayList<T>();
            for (var e = this; e != null; e = e.prev) {
                list.add(e.node);
            }
            Collections.reverse(list);
            path = Collections.unmodifiableList(list);
        }
        return path;
    }

}
