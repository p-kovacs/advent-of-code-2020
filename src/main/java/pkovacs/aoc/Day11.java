package pkovacs.aoc;

import pkovacs.aoc.util.InputUtils;

public class Day11 {

    public static void main(String[] args) {
        char[][] seats = InputUtils.readCharMatrix("day11.txt");

        System.out.println("Puzzle 1: " + new Puzzle(seats, false).solve());
        System.out.println("Puzzle 2: " + new Puzzle(seats, true).solve());
    }

    private static class Puzzle {

        char[][] seats;

        final int n;
        final int m;
        final int maxDist;
        final int maxOccupiedNeighbors;

        Puzzle(char[][] seats, boolean second) {
            this.seats = seats;
            n = seats.length;
            m = seats[0].length;
            maxDist = second ? Math.max(n - 1, m - 1) : 1;
            maxOccupiedNeighbors = second ? 4 : 3;
        }

        int solve() {
            boolean stable = false;
            while (!stable) {
                stable = true;
                char[][] newSeats = InputUtils.deepCopyOf(seats);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        int cnt = countOccupiedNeighbors(i, j);
                        if (seats[i][j] == 'L' && cnt == 0) {
                            newSeats[i][j] = '#';
                            stable = false;
                        } else if (seats[i][j] == '#' && cnt > maxOccupiedNeighbors) {
                            newSeats[i][j] = 'L';
                            stable = false;
                        }
                    }
                }
                seats = newSeats;
            }

            return countOccupiedSeats();
        }

        int countOccupiedNeighbors(int i, int j) {
            int cnt = 0;
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    // di, dj: direction
                    if (di == 0 && dj == 0) {
                        continue;
                    }
                    for (int dist = 1; dist <= maxDist; dist++) {
                        // ni, nj: neighbor coordinates in (di, dj) direction, dist distance
                        int ni = i + dist * di;
                        int nj = j + dist * dj;
                        if (ni >= 0 && ni < n && nj >= 0 && nj < m) {
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

        int countOccupiedSeats() {
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

}
