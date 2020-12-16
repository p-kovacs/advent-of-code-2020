package pkovacs.aoc.alg;

import org.junit.jupiter.api.Test;

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

}
