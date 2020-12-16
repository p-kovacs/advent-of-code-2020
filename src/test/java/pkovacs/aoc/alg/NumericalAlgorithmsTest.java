package pkovacs.aoc.alg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumericalAlgorithmsTest {

    @Test
    public void testIsPrime() {
        assertFalse(NumericalAlgorithms.isPrime(1));
        assertTrue(NumericalAlgorithms.isPrime(2));
        assertTrue(NumericalAlgorithms.isPrime(3));
        assertFalse(NumericalAlgorithms.isPrime(4));
        assertTrue(NumericalAlgorithms.isPrime(5));

        assertTrue(NumericalAlgorithms.isPrime(41));
        assertFalse(NumericalAlgorithms.isPrime(42));
        assertTrue(NumericalAlgorithms.isPrime(43));

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
//        assertTrue(NumericalAlgorithms.isPrime(2305843009213693951L)); // 3-4s
//        assertTrue(NumericalAlgorithms.isPrime(3318308475676071413L)); // 3-4s
//        assertTrue(NumericalAlgorithms.isPrime(3331113965338635107L)); // 3-4s
        assertFalse(NumericalAlgorithms.isPrime(27644437L * 1073676287L));

        assertThrows(IllegalArgumentException.class, () -> NumericalAlgorithms.isPrime(0));
        assertThrows(IllegalArgumentException.class, () -> NumericalAlgorithms.isPrime(-3));
    }

    @Test
    public void testGcd() {
        assertEquals(1, NumericalAlgorithms.calculateGcd(6, 35));
        assertEquals(3, NumericalAlgorithms.calculateGcd(15, 42));
        assertEquals(27644437L,
                NumericalAlgorithms.calculateGcd(27644437L * 1073676287L, 27644437L * 7919 * 41));
        assertEquals(41,
                NumericalAlgorithms.calculateGcd(3 * 41, 27644437L * 7919 * 41));
    }

    @Test
    public void testLcm() {
        assertEquals(6 * 35, NumericalAlgorithms.calculateLcm(6, 35));
        assertEquals(15 * 42 / 3, NumericalAlgorithms.calculateLcm(15, 42));
        assertEquals(1073676287L * 5189 * 7919 * 41,
                NumericalAlgorithms.calculateLcm(1073676287L * 5189, 1073676287L * 7919 * 41));
    }

}
