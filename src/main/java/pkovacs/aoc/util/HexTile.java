package pkovacs.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a hexagonal tile as an immutable pair of int values: row index and column index.
 */
public class HexTile {

    private static String[] DIRECTIONS = new String[] { "w", "e", "nw", "ne", "sw", "se" };

    public final int row;
    public final int col;

    public HexTile(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public HexTile getNeighbor(String dir) {
        if ("w".equals(dir)) {
            return new HexTile(row, col - 1);
        } else if ("e".equals(dir)) {
            return new HexTile(row, col + 1);
        } else if ("nw".equals(dir)) {
            return new HexTile(row - 1, col);
        } else if ("ne".equals(dir)) {
            return new HexTile(row - 1, col + 1);
        } else if ("sw".equals(dir)) {
            return new HexTile(row + 1, col - 1);
        } else if ("se".equals(dir)) {
            return new HexTile(row + 1, col);
        }
        throw new IllegalArgumentException();
    }

    public HexTile getTile(String path) {
        HexTile current = this;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == 'n' || path.charAt(i) == 's') {
                current = current.getNeighbor(path.substring(i, i + 2));
                i++;
            } else {
                current = current.getNeighbor(path.substring(i, i + 1));
            }
        }
        return current;
    }

    public List<HexTile> getNeighbors() {
        return getNeighbors(false);
    }

    public List<HexTile> getNeighbors(boolean includeSelf) {
        var neighbors = new ArrayList<HexTile>();
        if (includeSelf) {
            neighbors.add(this);
        }
        for (var dir : DIRECTIONS) {
            neighbors.add(getNeighbor(dir));
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HexTile other = (HexTile) o;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ')';
    }

}
