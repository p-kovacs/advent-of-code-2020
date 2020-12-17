package pkovacs.aoc.alg;

import java.util.List;

import org.junit.jupiter.api.Test;
import pkovacs.aoc.alg.NumericalAlgorithms.Congruence;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumericalAlgorithmsTest {

    @Test
    public void testIsPrime() {
        assertFalse(NumericalAlgorithms.isPrime(0));
        assertFalse(NumericalAlgorithms.isPrime(1));
        assertTrue(NumericalAlgorithms.isPrime(2));

        assertTrue(NumericalAlgorithms.isPrime(7919));
        assertFalse(NumericalAlgorithms.isPrime(7919 * 5189));
        assertTrue(NumericalAlgorithms.isPrime(27644437));
        assertTrue(NumericalAlgorithms.isPrime(1073676287));
        assertTrue(NumericalAlgorithms.isPrime(6643838879L));
        assertTrue(NumericalAlgorithms.isPrime(87178291199L));
        assertTrue(NumericalAlgorithms.isPrime(200560490131L));
        assertTrue(NumericalAlgorithms.isPrime(4398042316799L));
        assertTrue(NumericalAlgorithms.isPrime(688846502588399L));
        assertTrue(NumericalAlgorithms.isPrime(59604644783353249L));
        assertTrue(NumericalAlgorithms.isPrime(2305843009213693951L));
        assertTrue(NumericalAlgorithms.isPrime(3318308475676071413L));
        assertTrue(NumericalAlgorithms.isPrime(3331113965338635107L));
        assertFalse(NumericalAlgorithms.isPrime(27644437L * 1073676287L));

        assertThrows(IllegalArgumentException.class, () -> NumericalAlgorithms.isPrime(-3));
    }

    @Test
    public void testGcd() {
        assertEquals(1, NumericalAlgorithms.gcd(6, 35));
        assertEquals(3, NumericalAlgorithms.gcd(15, 42));
        assertEquals(27644437L,
                NumericalAlgorithms.gcd(27644437L * 1073676287L, 27644437L * 7919 * 41));
        assertEquals(41,
                NumericalAlgorithms.gcd(3 * 41, 27644437L * 7919 * 41));
    }

    @Test
    public void testLcm() {
        assertEquals(6 * 35, NumericalAlgorithms.lcm(6, 35));
        assertEquals(15 * 42 / 3, NumericalAlgorithms.lcm(15, 42));
        assertEquals(1073676287L * 5189 * 7919 * 41,
                NumericalAlgorithms.lcm(1073676287L * 5189, 1073676287L * 7919 * 41));
    }

    @Test
    public void testCongruence() {
        assertThrows(IllegalArgumentException.class, () -> new Congruence(0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Congruence(-1, 0));

        var c1 = new Congruence(5, 2);
        var c2 = new Congruence(5, 7);
        var c3 = new Congruence(5, -3);
        assertEquals(c1, c2);
        assertEquals(c1, c3);

        var list = List.of(new Congruence(5, 0),
                new Congruence(123, 32),
                new Congruence(11, 11));
        assertEquals("[x % 123 == 32, x % 11 == 0, x % 5 == 0]",
                list.stream().sorted().collect(toList()).toString());
    }

    @Test
    public void testCrt() {
        assertEquals(23, NumericalAlgorithms.solveCrt(
                List.of(new Congruence(3, 2),
                        new Congruence(5, 3),
                        new Congruence(7, 2))));
        assertEquals(39, NumericalAlgorithms.solveCrt(
                List.of(new Congruence(3, 0),
                        new Congruence(4, 3),
                        new Congruence(5, 4))));
        assertEquals(39, NumericalAlgorithms.solveCrt(
                List.of(new Congruence(3, 0),
                        new Congruence(4, 3),
                        new Congruence(5, 4))));
        assertEquals(560214575859998L, NumericalAlgorithms.solveCrt(
                List.of(new Congruence(373, 323),
                        new Congruence(367, 348),
                        new Congruence(41, 32),
                        new Congruence(37, 24),
                        new Congruence(29, 10),
                        new Congruence(23, 19),
                        new Congruence(19, 0),
                        new Congruence(17, 15),
                        new Congruence(13, 7))));
    }

}
