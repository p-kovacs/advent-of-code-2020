package pkovacs.aoc.util;

import java.util.Objects;

/**
 * Represents an immutable pair of int values.
 */
public class IntPair {

    public final int a;
    public final int b;

    public IntPair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntPair other = (IntPair) o;
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
