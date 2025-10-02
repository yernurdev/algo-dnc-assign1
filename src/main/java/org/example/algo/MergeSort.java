package org.example.algo;

import org.example.util.Metrics;

public class MergeSort {
    private static final int INSERTION_SORT_THRESHOLD = 16;

    public static void sort(int[] arr, Metrics metrics) {
        metrics.reset();
        metrics.startTimer();
        metrics.enterRecursion();

        int[] buffer = new int[arr.length];
        metrics.incAllocations(); // выделение буфера
        mergeSort(arr, buffer, 0, arr.length - 1, metrics);

        metrics.exitRecursion();
        metrics.stopTimer();
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right, Metrics metrics) {
        metrics.enterRecursion();
        if (right - left + 1 <= INSERTION_SORT_THRESHOLD) {
            insertionSort(arr, left, right, metrics);
            metrics.exitRecursion();
            return;
        }

        int mid = (left + right) >>> 1;
        mergeSort(arr, buffer, left, mid, metrics);
        mergeSort(arr, buffer, mid + 1, right, metrics);
        merge(arr, buffer, left, mid, right, metrics);

        metrics.exitRecursion();
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

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics metrics) {
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            metrics.incComparisons();
            if (arr[i] <= arr[j]) {
                buffer[k++] = arr[i++];
            } else {
                buffer[k++] = arr[j++];
            }
        }
        while (i <= mid) buffer[k++] = arr[i++];
        while (j <= right) buffer[k++] = arr[j++];

        // копируем обратно
        for (int t = left; t <= right; t++) {
            arr[t] = buffer[t];
        }
    }
}
