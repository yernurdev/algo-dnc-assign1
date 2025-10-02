package org.example.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsTest {

    @Test
    void testRecursionDepth() {
        Metrics m = new Metrics();
        m.enterRecursion();
        m.enterRecursion();
        assertEquals(2, m.getMaxDepth());
        m.exitRecursion();
        assertEquals(1, m.getMaxDepth());
    }

    @Test
    void testComparisonsAndAllocations() {
        Metrics m = new Metrics();
        m.incComparisons();
        m.incAllocations();
        assertEquals(1, m.getComparisons());
        assertEquals(1, m.getAllocations());
    }

    @Test
    void testTimer() throws InterruptedException {
        Metrics m = new Metrics();
        m.startTimer();
        Thread.sleep(10);
        m.stopTimer();
        assertTrue(m.getTimeMs() >= 10);
    }
}
