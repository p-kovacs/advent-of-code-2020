package pkovacs.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * Represents a tile (cell) in a table or matrix as an immutable pair of int values:
 * row index and column index.
 */
public class Tile {

    public final int row;
    public final int col;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isValid(int rowCount, int colCount) {
        return row >= 0 && row < rowCount && col >= 0 && col < colCount;
    }

    public List<Tile> getFourNeighbors() {
        return getNeighbors((r, c) -> r != row ^ c != col);
    }

    public List<Tile> getEightNeighbors() {
        return getNeighbors((r, c) -> r != row || c != col);
    }

    public List<Tile> getEightNeighborsAndSelf() {
        return getNeighbors((r, c) -> true);
    }

    private List<Tile> getNeighbors(BiPredicate<Integer, Integer> predicate) {
        var neighbors = new ArrayList<Tile>(8);
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (predicate.test(r, c)) {
                    neighbors.add(new Tile(r, c));
                }
            }
        }
        return neighbors;
    }

    public int getManhattanDistance(Tile c) {
        return getManhattanDistance(this, c);
    }

    public static int getManhattanDistance(Tile c1, Tile c2) {
        return Math.abs(c1.row - c2.row) + Math.abs(c1.col - c2.col);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tile other = (Tile) o;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }

}
