package com.github.pkovacs.aoc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.math.LongMath;

/**
 * Represents a point in D-dimensional (D >= 1) space with integer coordinates and Manhattan distance.
 * This class is a D-dimensional generalization of {@link Tile}.
 */
public class Point {

    private final int[] coordinates;

    public Point(int... coordinates) {
        this.coordinates = coordinates;
    }

    public int dim() {
        return coordinates.length;
    }

    public int get(int k) {
        return coordinates[k];
    }

    public int length() {
        return Arrays.stream(coordinates).map(Math::abs).sum();
    }

    public static Point add(Point a, Point b) {
        var coords = new int[a.coordinates.length];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = a.coordinates[i] + b.coordinates[i];
        }
        return new Point(coords);
    }

    public static Point sub(Point a, Point b) {
        var coords = new int[a.coordinates.length];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = a.coordinates[i] - b.coordinates[i];
        }
        return new Point(coords);
    }

    public static int dist(Point a, Point b) {
        return sub(a, b).length();
    }

    public List<Point> getNeighbors() {
        return getNeighbors(false);
    }

    public List<Point> getNeighbors(boolean includeSelf) {
        int dim = coordinates.length;
        var result = new ArrayList<Point>((int) LongMath.pow(3, dim));
        result.add(this);
        for (int k = 0; k < dim; k++) {
            for (int i = 0, origSize = result.size(); i < origSize; i++) {
                var p = result.get(i);
                result.add(p.changeCoordinate(k, -1));
                result.add(p.changeCoordinate(k, 1));
            }
        }
        return includeSelf ? result : result.subList(1, result.size());
    }

    private Point changeCoordinate(int k, int delta) {
        int[] c = coordinates.clone();
        c[k] += delta;
        return new Point(c);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(coordinates, ((Point) o).coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }

    @Override
    public String toString() {
        return Arrays.toString(coordinates);
    }

}
