package org.example.algo;

import org.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void testSmallArray() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        Metrics m = new Metrics();
        QuickSort.sort(arr, m);
        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, arr);
    }

    @Test
    void testRandomArray() {
        Random rnd = new Random(42);
        int n = 2000;
        int[] arr = rnd.ints(n, -10000, 10000).toArray();
        int[] copy = Arrays.copyOf(arr, n);
        Metrics m = new Metrics();
        QuickSort.sort(arr, m);
        Arrays.sort(copy);
        assertArrayEquals(copy, arr);
    }

    @Test
    void testSortedAndReverse() {
        Metrics m1 = new Metrics();
        int[] sorted = {1,2,3,4,5,6,7};
        QuickSort.sort(sorted, m1);
        assertArrayEquals(new int[]{1,2,3,4,5,6,7}, sorted);

        Metrics m2 = new Metrics();
        int[] rev = new int[]{7,6,5,4,3,2,1};
        QuickSort.sort(rev, m2);
        assertArrayEquals(new int[]{1,2,3,4,5,6,7}, rev);
    }

    @Test
    void testManyDuplicates() {
        Metrics m = new Metrics();
        int[] a = new int[1000];
        Arrays.fill(a, 42);
        QuickSort.sort(a, m);
        for (int i = 1; i < a.length; i++) {
            assertTrue(a[i-1] <= a[i]);
        }
    }
}
