package org.example.algo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

class ClosestPairTest {

    @Test
    void testSimpleCase() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4),
                new ClosestPair.Point(1, 1)
        };
        double result = ClosestPair.closestPair(points);
        assertEquals(Math.sqrt(2), result, 1e-9);
    }

    @Test
    void testTwoPoints() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(5, 5),
                new ClosestPair.Point(6, 5)
        };
        double result = ClosestPair.closestPair(points);
        assertEquals(1.0, result, 1e-9);
    }

    @Test
    void testRandomVsBruteForce() {
        Random rnd = new Random(42);
        for (int n = 5; n <= 200; n += 20) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
            }
            double fast = ClosestPair.closestPair(pts);
            double brute = bruteForce(pts);
            assertEquals(brute, fast, 1e-6, "Mismatch at n=" + n);
        }
    }

    private double bruteForce(ClosestPair.Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < min) min = dist;
            }
        }
        return min;
    }
}
