package org.example.algo;

import org.example.util.Metrics;
import java.util.Arrays;

public class DeterministicSelect {

    // Публичный метод с метриками
    public static int select(int[] arr, int left, int right, int k, Metrics metrics) {
        metrics.reset();
        metrics.startTimer();
        metrics.enterRecursion();
        int result = selectRec(arr, left, right, k, metrics);
        metrics.exitRecursion();
        metrics.stopTimer();
        return result;
    }

    // Внутренняя рекурсивная функция
    private static int selectRec(int[] arr, int left, int right, int k, Metrics metrics) {
        metrics.enterRecursion();
        try {
            if (left == right) return arr[left];

            // Находим pivot с помощью median-of-medians
            int pivotIndex = medianOfMedians(arr, left, right, metrics);
            pivotIndex = partition(arr, left, right, pivotIndex, metrics);

            int length = pivotIndex - left + 1;
            if (k == length) return arr[pivotIndex];
            else if (k < length) return selectRec(arr, left, pivotIndex - 1, k, metrics);
            else return selectRec(arr, pivotIndex + 1, right, k - length, metrics);
        } finally {
            metrics.exitRecursion();
        }
    }

    // Разбиение массива на элементы меньше/больше pivot
    private static int partition(int[] arr, int left, int right, int pivotIndex, Metrics metrics) {
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int store = left;
        for (int i = left; i < right; i++) {
            metrics.incComparisons();
            if (arr[i] < pivot) swap(arr, store++, i);
        }
        swap(arr, store, right);
        metrics.incAllocations(); // учитываем создание новых позиций
        return store;
    }

    // Median-of-Medians
    private static int medianOfMedians(int[] arr, int left, int right, Metrics metrics) {
        int n = right - left + 1;
        if (n < 5) {
            Arrays.sort(arr, left, right + 1);
            metrics.incComparisons(); // сортировка учитывает сравнения
            return left + n / 2;
        }

        int numMedians = (int) Math.ceil((double) n / 5);
        for (int i = 0; i < numMedians; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            Arrays.sort(arr, subLeft, subRight + 1);
            metrics.incComparisons();
            int median = subLeft + (subRight - subLeft) / 2;
            swap(arr, left + i, median);
            metrics.incAllocations();
        }

        return medianOfMedians(arr, left, left + numMedians - 1, metrics);
    }

    // Простой swap
    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }
}
