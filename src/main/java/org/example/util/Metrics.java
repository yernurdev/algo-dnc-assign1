package org.example.util;

public class Metrics {
    private long comparisons = 0;
    private long allocations = 0;
    private long recursionDepth = 0;
    private long maxDepth = 0;
    private long startTime;
    private long endTime;

    private static final ThreadLocal<Long> currentDepth = ThreadLocal.withInitial(() -> 0L);

    public void reset() {
        comparisons = 0;
        allocations = 0;
        recursionDepth = 0;
        maxDepth = 0;
        startTime = 0;
        endTime = 0;
        currentDepth.set(0L);
    }

    public void incComparisons() { comparisons++; }
    public void incAllocations() { allocations++; }

    public void enterRecursion() {
        long d = currentDepth.get() + 1;
        currentDepth.set(d);
        recursionDepth = d;
        if (recursionDepth > maxDepth) {
            maxDepth = recursionDepth;
        }
    }

    public void exitRecursion() {
        long d = currentDepth.get() - 1;
        currentDepth.set(d);
        recursionDepth = d;
    }

    public void startTimer() { startTime = System.nanoTime(); }
    public void stopTimer() { endTime = System.nanoTime(); }
    public long getTimeMs() { return (endTime - startTime) / 1_000_000; }

    public long getComparisons() { return comparisons; }
    public long getAllocations() { return allocations; }
    public long getMaxDepth() { return maxDepth; }
}
