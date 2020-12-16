package pkovacs.aoc.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import pkovacs.aoc.util.LongPair;

/**
 * Implements basic numerical algorithms.
 */
public final class NumericalAlgorithms {

    private static final int[] PRIME_NUMBERS = new int[] {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
            73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
            179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
            283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
            419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541,
    };

    private NumericalAlgorithms() {
    }

    /**
     * Checks if the given positive integer is a prime number or not. For small numbers, an array lookup is used,
     * otherwise divisors 2, 3, 6k - 1, 6k + 1 are checked until reaching sqrt(a).
     */
    public static boolean isPrime(long a) {
        if (a <= 0) {
            throw new IllegalArgumentException("A positive integer required.");
        }
        if (a <= PRIME_NUMBERS[PRIME_NUMBERS.length - 1]) {
            return Arrays.binarySearch(PRIME_NUMBERS, (int) a) >= 0;
        }
        if (a % 2 == 0 || a % 3 == 0) {
            return false;
        }
        for (long d = 5; d * d < a; d += 6) {
            if (a % d == 0 || a % (d + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates the greatest common divisor (GCD) of the given two positive integers using
     * Euclid's algorithm: https://en.wikipedia.org/wiki/Euclidean_algorithm
     */
    public static long calculateGcd(long a, long b) {
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("Positive integers required.");
        }
        long x = a, y = b;
        while (y != 0) {
            long z = y;
            y = x % y;
            x = z;
        }
        return x;
    }

    /**
     * Calculates the least common multiple (LCM) of the given two positive integers by dividing
     * {@code a * b} with the GCD of the numbers.
     *
     * @see #calculateGcd(long, long)
     */
    public static long calculateLcm(long a, long b) {
        return a / calculateGcd(a, b) * b; // operations are applied in this order to avoid overflow
    }

}
