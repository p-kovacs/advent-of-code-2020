package com.github.pkovacs.aoc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.math.LongMath;

/**
 * Represents a point in D-dimensional (D >= 1) space with integer coordinates and Manhattan distance.
 */
public final class Vector {

    private final int[] coordinates;

    public Vector(int... coordinates) {
        this.coordinates = coordinates;
    }

    public int dim() {
        return coordinates.length;
    }

    public int get(int k) {
        return coordinates[k];
    }

    public int dist() {
        return Arrays.stream(coordinates).map(Math::abs).sum();
    }

    public List<Vector> getNeighbors() {
        return getNeighbors(false);
    }

    public List<Vector> getNeighborsAndSelf() {
        return getNeighbors(true);
    }

    private List<Vector> getNeighbors(boolean includeSelf) {
        int dim = coordinates.length;
        var result = new ArrayList<Vector>((int) LongMath.pow(3, dim));
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

    private Vector changeCoordinate(int k, int delta) {
        int[] c = coordinates.clone();
        c[k] += delta;
        return new Vector(c);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(coordinates, ((Vector) o).coordinates);
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
