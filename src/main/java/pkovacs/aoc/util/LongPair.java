package pkovacs.aoc.util;

import java.util.Objects;

/**
 * Represents an immutable pair of long values.
 */
public class LongPair {

    public final long a;
    public final long b;

    public LongPair(long a, long b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LongPair other = (LongPair) o;
        return a == other.a && b == other.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "(" + a + "," + b + ")";
    }

}
