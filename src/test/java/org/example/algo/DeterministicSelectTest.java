package org.example.algo;

import org.junit.jupiter.api.Test;
import org.example.util.Metrics;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {

    @Test
    void testSmallFixedArray() {
        int[] arr = {9, 1, 7, 3, 2, 8, 6};
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        for (int k = 1; k <= arr.length; k++) {
            Metrics metrics = new Metrics();
            int result = DeterministicSelect.select(arr.clone(), 0, arr.length - 1, k, metrics);
            assertEquals(sorted[k - 1], result, "Mismatch at k=" + k);
        }
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        Metrics metrics = new Metrics();
        assertEquals(42, DeterministicSelect.select(arr, 0, 0, 1, metrics));
    }

    @Test
    void testAllEqualElements() {
        int[] arr = {5, 5, 5, 5, 5};
        for (int k = 1; k <= arr.length; k++) {
            Metrics metrics = new Metrics();
            assertEquals(5, DeterministicSelect.select(arr.clone(), 0, arr.length - 1, k, metrics));
        }
    }

    @Test
    void testRandomArrays() {
        Random rnd = new Random(123);
        for (int n = 5; n <= 200; n += 20) {
            int[] arr = rnd.ints(n, 0, 1000).toArray();
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            for (int k = 1; k <= n; k++) {
                Metrics metrics = new Metrics();
                int result = DeterministicSelect.select(arr.clone(), 0, arr.length - 1, k, metrics);
                assertEquals(sorted[k - 1], result, "Mismatch at k=" + k + " for n=" + n);
            }
        }
    }
}
