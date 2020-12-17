package pkovacs.aoc;

import pkovacs.aoc.util.InputUtils;

public class Day17 {

    public static void main(String[] args) {
        var input = InputUtils.readCharMatrix("day17.txt");

        int cycleCount = 6;
        int n = input.length + 2 * (cycleCount + 2); // size of each dimension of the space

        System.out.println("Part 1: " + solveDim3(input, n, cycleCount));
        System.out.println("Part 2: " + solveDim4(input, n, cycleCount));
    }

    private static long solveDim3(char[][] input, int n, int cycleCount) {
        var space = new boolean[n][n][n];

        int base = (n - input.length) / 2;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[x].length; y++) {
                space[base + x][base + y][base] = input[y][x] == '#';
            }
        }

        for (int cyc = 0; cyc < cycleCount; cyc++) {
            var newSpace = new boolean[n][n][n];
            for (int x = 1; x < space.length - 1; x++) {
                for (int y = 1; y < space[x].length - 1; y++) {
                    for (int z = 1; z < space[x][y].length - 1; z++) {
                        int nc = countActiveNeighbors(space, x, y, z);
                        if (space[x][y][z] && nc != 2 && nc != 3) {
                            newSpace[x][y][z] = false;
                        } else if (!space[x][y][z] && nc == 3) {
                            newSpace[x][y][z] = true;
                        } else {
                            newSpace[x][y][z] = space[x][y][z];
                        }
                    }
                }
            }
            space = newSpace;
        }

        return countActive(space);
    }

    private static long solveDim4(char[][] input, int n, int cycleCount) {
        var space = new boolean[n][n][n][n];

        int base = (n - input.length) / 2;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[x].length; y++) {
                space[base + x][base + y][base][base] = input[y][x] == '#';
            }
        }

        for (int cyc = 0; cyc < cycleCount; cyc++) {
            var newSpace = new boolean[n][n][n][n];
            for (int x = 1; x < space.length - 1; x++) {
                for (int y = 1; y < space[x].length - 1; y++) {
                    for (int z = 1; z < space[x][y].length - 1; z++) {
                        for (int w = 1; w < space[x][y][z].length - 1; w++) {
                            int nc = countActiveNeighbors(space, x, y, z, w);
                            if (space[x][y][z][w] && nc != 2 && nc != 3) {
                                newSpace[x][y][z][w] = false;
                            } else if (!space[x][y][z][w] && nc == 3) {
                                newSpace[x][y][z][w] = true;
                            } else {
                                newSpace[x][y][z][w] = space[x][y][z][w];
                            }
                        }
                    }
                }
            }
            space = newSpace;
        }

        return countActive(space);
    }

    private static int countActiveNeighbors(boolean[][][] space, int x, int y, int z) {
        int cnt = 0;
        for (int xx = x - 1; xx <= x + 1; xx++) {
            for (int yy = y - 1; yy <= y + 1; yy++) {
                for (int zz = z - 1; zz <= z + 1; zz++) {
                    if ((xx != x || yy != y || zz != z) && space[xx][yy][zz]) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    private static int countActiveNeighbors(boolean[][][][] space, int x, int y, int z, int w) {
        int cnt = 0;
        for (int xx = x - 1; xx <= x + 1; xx++) {
            for (int yy = y - 1; yy <= y + 1; yy++) {
                for (int zz = z - 1; zz <= z + 1; zz++) {
                    for (int ww = w - 1; ww <= w + 1; ww++) {
                        if ((xx != x || yy != y || zz != z || ww != w) && space[xx][yy][zz][ww]) {
                            cnt++;
                        }
                    }
                }
            }
        }
        return cnt;
    }

    private static int countActive(boolean[][][] space) {
        int cnt = 0;
        for (boolean[][] matrix : space) {
            for (boolean[] array : matrix) {
                for (boolean b : array) {
                    if (b) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    private static int countActive(boolean[][][][] space) {
        int cnt = 0;
        for (boolean[][][] subspace : space) {
            cnt += countActive(subspace);
        }
        return cnt;
    }

}
