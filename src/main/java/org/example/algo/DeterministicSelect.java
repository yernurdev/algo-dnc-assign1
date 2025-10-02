package org.example.algo;

import java.util.Arrays;

public class DeterministicSelect {

    public static int select(int[] arr, int left, int right, int k) {
        if (left == right) return arr[left];

        // 1. Найти pivot через Median-of-Medians
        int pivotIndex = medianOfMedians(arr, left, right);
        pivotIndex = partition(arr, left, right, pivotIndex);

        int length = pivotIndex - left + 1;
        if (k == length) return arr[pivotIndex];
        else if (k < length) return select(arr, left, pivotIndex - 1, k);
        else return select(arr, pivotIndex + 1, right, k - length);
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int store = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivot) swap(arr, store++, i);
        }
        swap(arr, store, right);
        return store;
    }

    private static int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;
        if (n < 5) {
            Arrays.sort(arr, left, right + 1);
            return left + n / 2;
        }
        int numMedians = (int) Math.ceil((double) n / 5);
        for (int i = 0; i < numMedians; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            Arrays.sort(arr, subLeft, subRight + 1);
            int median = subLeft + (subRight - subLeft) / 2;
            swap(arr, left + i, median);
        }
        return medianOfMedians(arr, left, left + numMedians - 1);
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }
}
