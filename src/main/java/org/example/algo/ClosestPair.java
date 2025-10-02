package org.example.algo;

import org.example.util.Metrics;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    // Новый метод с метриками
    public static double closestPair(Point[] points, Metrics metrics) {
        metrics.reset();
        metrics.startTimer();
        metrics.enterRecursion();

        Point[] pts = Arrays.copyOf(points, points.length);
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));
        double result = closestPairRec(pts, 0, pts.length - 1, metrics);

        metrics.exitRecursion();
        metrics.stopTimer();
        return result;
    }

    private static double closestPairRec(Point[] pts, int left, int right, Metrics metrics) {
        metrics.enterRecursion();
        try {
            if (right - left <= 3) {
                return bruteForce(pts, left, right, metrics);
            }

            int mid = (left + right) / 2;
            double midX = pts[mid].x;

            double d1 = closestPairRec(pts, left, mid, metrics);
            double d2 = closestPairRec(pts, mid + 1, right, metrics);
            double d = Math.min(d1, d2);

            Point[] strip = new Point[right - left + 1];
            int sz = 0;
            for (int i = left; i <= right; i++) {
                metrics.incComparisons();
                if (Math.abs(pts[i].x - midX) < d) strip[sz++] = pts[i];
            }

            Arrays.sort(strip, 0, sz, Comparator.comparingDouble(p -> p.y));
            for (int i = 0; i < sz; i++) {
                for (int j = i + 1; j < sz && (strip[j].y - strip[i].y) < d; j++) {
                    metrics.incComparisons();
                    d = Math.min(d, dist(strip[i], strip[j]));
                }
            }

            return d;
        } finally {
            metrics.exitRecursion();
        }
    }

    private static double bruteForce(Point[] pts, int left, int right, Metrics metrics) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.incComparisons();
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        metrics.incAllocations(); // можно считать создание временных массивов
        return min;
    }

    private static double dist(Point a, Point b) {
        return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
    }
}
