package org.example.algo;

import org.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MergeSortTest {
    @Test
    void testSmallArray() {
        int[] arr = {5, 2, 4, 6, 1, 3};
        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        assertTrue(isSorted(arr));
    }

    @Test
    void testRandomArray() {
        Random rand = new Random(42);
        int[] arr = new int[1000];
        for (int i = 0; i < arr.length; i++) arr[i] = rand.nextInt();
        int[] copy = Arrays.copyOf(arr, arr.length);

        Metrics m = new Metrics();
        MergeSort.sort(arr, m);
        Arrays.sort(copy);

        assertTrue(Arrays.equals(arr, copy));
    }

    private boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }
}
