package pkovacs.aoc.util;

import java.util.Objects;

/**
 * Represents an immutable pair of long values.
 */
public class LongPair {

    public final long x;
    public final long y;

    public LongPair(long x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LongPair other = (LongPair) o;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
