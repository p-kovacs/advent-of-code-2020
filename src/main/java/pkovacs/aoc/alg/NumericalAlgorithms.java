package pkovacs.aoc.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.math.LongMath;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

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

    /**
     * Finds a non-negative integer satisfying a list of congruences based on the Chinese remainder theorem.
     * The congruences are defined by the given divisors and remainders, where the divisors must be pairwise coprime.
     */
    public static long solveCrt(List<Congruence> congruences) {
        // The applied simple algorithm is described on these pages:
        // https://en.wikipedia.org/wiki/Chinese_remainder_theorem#Search_by_sieving
        // https://www.di-mgt.com.au/crt.html

        // Sort congruences to improve performance
        var sortedCongruences = congruences.stream().sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        // Check that the divisors are pairwise coprime
        for (int i = 0; i < sortedCongruences.size(); i++) {
            for (int j = i+1; j < sortedCongruences.size(); j++) {
                checkCoprimeDivisors(sortedCongruences.get(i), sortedCongruences.get(j));
            }
        }

        // Find the smallest appropriate number
        long n = 0;
        long step = 1;
        for (var c : sortedCongruences) {
            while (n % c.divisor != c.remainder) {
                n += step;
            }
            step *= c.divisor;
        }

        return n;
    }

    private static void checkCoprimeDivisors(Congruence c1, Congruence c2) {
        if (gcd(c1.divisor, c2.divisor) > 1) {
            throw new IllegalArgumentException(
                    "Divisors are not coprime: " + c1.divisor + " and " + c2.divisor + ".");
        }
    }

    public static class Congruence implements Comparable<Congruence> {

        public final long divisor;
        public final long remainder;

        public Congruence(long divisor, long remainder) {
            if (divisor < 1) {
                throw new IllegalArgumentException("Illegal divisor: " + divisor + ".");
            }
            this.divisor = divisor;
            this.remainder = LongMath.mod(remainder, divisor);
        }

        @Override
        public int compareTo(Congruence other) {
            return Long.compare(other.divisor, divisor); // decreasing order by divisor
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Congruence other = (Congruence) o;
            return divisor == other.divisor && remainder == other.remainder;
        }

        @Override
        public int hashCode() {
            return Objects.hash(divisor, remainder);
        }

        @Override
        public String toString() {
            return String.format("x %% %d == %d", divisor, remainder);
        }

    }

}
