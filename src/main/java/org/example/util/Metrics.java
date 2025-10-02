package org.example.util;

public class Metrics {
    // --- recursion depth tracking ---
    private int currentDepth = 0;   // текущая глубина
    private int peakDepth = 0;      // максимальная достигнутая глубина

    // --- operation counters ---
    private long comparisons = 0;
    private long allocations = 0;

    // --- timing ---
    private long startTime;
    private long elapsedMs;

    // ===== Recursion =====
    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > peakDepth) {
            peakDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        if (currentDepth > 0) {
            currentDepth--;
        }
    }

    /** Текущая глубина рекурсии */
    public int getMaxDepth() {
        return currentDepth;
    }

    /** Исторический максимум (для отчётов) */
    public int getPeakDepth() {
        return peakDepth;
    }

    // ===== Comparisons & Allocations =====
    public void incComparisons() {
        comparisons++;
    }

    public void addComparisons(long c) {
        comparisons += c;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void incAllocations() {
        allocations++;
    }

    public void addAllocations(long a) {
        allocations += a;
    }

    public long getAllocations() {
        return allocations;
    }

    // ===== Timing =====
    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        elapsedMs = (System.nanoTime() - startTime) / 1_000_000;
    }

    public long getTimeMs() {
        return elapsedMs;
    }

    // ===== Reset =====
    public void reset() {
        currentDepth = 0;
        peakDepth = 0;
        comparisons = 0;
        allocations = 0;
        elapsedMs = 0;
    }
}
