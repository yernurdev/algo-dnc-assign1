package org.example.algo;

import org.example.util.Metrics;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {
    private static final int INSERTION_SORT_THRESHOLD = 16;

    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length < 2) return;
        metrics.reset();
        metrics.startTimer();

        quickSort(arr, 0, arr.length - 1, metrics);

        metrics.stopTimer();
    }

    private static void quickSort(int[] a, int lo, int hi, Metrics metrics) {
        while (lo < hi) {
            metrics.enterRecursion();
            try {
                int len = hi - lo + 1;
                if (len <= INSERTION_SORT_THRESHOLD) {
                    insertionSort(a, lo, hi, metrics);
                    return;
                }

                // Random pivot: swap random element to hi
                int pivotIdx = ThreadLocalRandom.current().nextInt(lo, hi + 1);
                swap(a, pivotIdx, hi);

                int p = partition(a, lo, hi, metrics);

                int leftSize = p - lo;
                int rightSize = hi - p;

                // Recurse on smaller side, iterate on larger
                if (leftSize < rightSize) {
                    quickSort(a, lo, p - 1, metrics);
                    lo = p + 1; // tail: process right part in loop
                } else {
                    quickSort(a, p + 1, hi, metrics);
                    hi = p - 1; // tail: process left part in loop
                }
            } finally {
                metrics.exitRecursion();
            }
        }
    }

    private static int partition(int[] a, int lo, int hi, Metrics metrics) {
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            metrics.incComparisons();
            if (a[j] < pivot) {
                swap(a, i++, j);
            }
        }
        swap(a, i, hi);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        // не считаем аллокации для swap; можно считать вручную, если нужно:
        // metrics.incAllocations();
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                metrics.incComparisons();
                if (arr[j] <= key) break;
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}
