package pkovacs.aoc;

import pkovacs.aoc.util.AocUtils;

public class Day11 {

    public static void main(String[] args) {
        char[][] seats = AocUtils.readCharMatrix("day11.txt");

        System.out.println("Puzzle 1: " + solvePuzzle(seats, false));
        System.out.println("Puzzle 2: " + solvePuzzle(seats, true));
    }

    private static int solvePuzzle(char[][] seats, boolean second) {
        int n = seats.length;
        int m = seats[0].length;
        int maxDist = second ? Math.max(n - 1, m - 1) : 1;
        int maxOccupiedNeighbors = second ? 4 : 3;

        boolean change = true;
        while (change) {
            change = false;
            char[][] newSeats = AocUtils.deepCopyOf(seats);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int cnt = countOccupiedNeighbors(seats, i, j, maxDist);
                    if (seats[i][j] == 'L' && cnt == 0) {
                        newSeats[i][j] = '#';
                        change = true;
                    } else if (seats[i][j] == '#' && cnt > maxOccupiedNeighbors) {
                        newSeats[i][j] = 'L';
                        change = true;
                    }
                }
            }
            seats = newSeats;
        }

        return countOccupiedSeats(seats);
    }

    private static int countOccupiedNeighbors(char[][] seats, int i, int j, int maxDist) {
        int cnt = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) {
                    continue;
                }
                for (int dist = 1; dist <= maxDist; dist++) {
                    int ni = i + dist * di;
                    int nj = j + dist * dj;
                    if (ni >= 0 && ni < seats.length && nj >= 0 && nj < seats[i].length) {
                        if (seats[ni][nj] == '#') {
                            cnt++;
                            break;
                        } else if (seats[ni][nj] == 'L') {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return cnt;
    }

    private static int countOccupiedSeats(char[][] seats) {
        int cnt = 0;
        for (char[] seat : seats) {
            for (char c : seat) {
                if (c == '#') {
                    cnt++;
                }
            }
        }
        return cnt;
    }

}
