package pkovacs.aoc.util;

import java.util.Objects;

/**
 * Represents a 2-dimensional mutable vector with integer (long) coordinates and Manhattan distance/length.
 * <p>
 * The coordinates are interpreted as usual in Math: (0,1) means "north", (0,-1) means "south",
 * (1,0) means "east", and (0,1) means "west".
 */
public class Vector {

    public long x;
    public long y;

    public Vector(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector v) {
        this(v.x, v.y);
    }

    public void set(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector v) {
        set(v.x, v.y);
    }

    public void add(long dx, long dy) {
        set(x + dx, y + dy);
    }

    public void add(Vector v) {
        add(v.x, v.y);
    }

    public void neg() {
        set(-x, -y);
    }

    public void rotateRight() {
        set(y, -x);
    }

    public void rotateLeft() {
        set(-y, x);
    }

    public long length() {
        return Math.abs(x) + Math.abs(y);
    }

    public static Vector add(Vector a, Vector b) {
        return new Vector(a.x + b.x, a.y + b.y);
    }

    public static Vector sub(Vector a, Vector b) {
        return new Vector(a.x - b.x, a.y - b.y);
    }

    public static long dist(Vector a, Vector b) {
        return sub(a, b).length();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vector other = (Vector) o;
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
