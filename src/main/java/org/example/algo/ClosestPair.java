package org.example.algo;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static class Point {
        public final double x, y;
        public Point(double x, double y) {
            this.x = x; this.y = y;
        }
    }

    public static double closestPair(Point[] points) {
        // сортируем по x
        Point[] pts = Arrays.copyOf(points, points.length);
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));
        return closestPairRec(pts, 0, pts.length - 1);
    }

    private static double closestPairRec(Point[] pts, int left, int right) {
        if (right - left <= 3) {
            return bruteForce(pts, left, right);
        }

        int mid = (left + right) / 2;
        double midX = pts[mid].x;

        double d1 = closestPairRec(pts, left, mid);
        double d2 = closestPairRec(pts, mid + 1, right);
        double d = Math.min(d1, d2);

        // собрать "полосу" кандидатов
        Point[] strip = new Point[right - left + 1];
        int sz = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(pts[i].x - midX) < d) {
                strip[sz++] = pts[i];
            }
        }

        Arrays.sort(strip, 0, sz, Comparator.comparingDouble(p -> p.y));
        for (int i = 0; i < sz; i++) {
            for (int j = i + 1; j < sz && (strip[j].y - strip[i].y) < d; j++) {
                d = Math.min(d, dist(strip[i], strip[j]));
            }
        }

        return d;
    }

    private static double bruteForce(Point[] pts, int left, int right) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
