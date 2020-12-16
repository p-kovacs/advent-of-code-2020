package pkovacs.aoc.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.math.LongMath;
import pkovacs.aoc.util.LongPair;

/**
 * Implements basic numerical algorithms.
 */
public final class NumericalAlgorithms {

    private NumericalAlgorithms() {
    }

    /**
     * Checks if the given non-negative integer is prime by calling {@link LongMath#isPrime(long)}.
     */
    public static boolean isPrime(long a) {
        return LongMath.isPrime(a);
    }

    /**
     * Calculates the greatest common divisor (GCD) of the given two non-negative integers by calling
     * {@link LongMath#gcd(long, long)}.
     */
    public static long gcd(long a, long b) {
        return LongMath.gcd(a, b);
    }

    /**
     * Calculates the least common multiple (LCM) of the given two non-negative integers using
     * {@link LongMath#gcd(long, long)}.
     */
    public static long lcm(long a, long b) {
        return a / LongMath.gcd(a, b) * b; // operations are applied in this order to avoid overflow
    }

}
