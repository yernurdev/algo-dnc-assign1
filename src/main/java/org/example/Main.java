package org.example;

import org.example.algo.*;
import org.example.util.*;

public class Main {
    public static void main(String[] args) {
        String algo = null;
        int size = 1000;
        String output = "metrics.csv";

        for (int i = 0; i < args.length; i++) {
            switch(args[i]) {
                case "--algo": algo = args[++i]; break;
                case "--size": size = Integer.parseInt(args[++i]); break;
                case "--output": output = args[++i]; break;
            }
        }

        if (algo == null) {
            System.out.println("Specify algorithm with --algo [mergesort|quicksort|select|closest]");
            return;
        }

        Metrics metrics = new Metrics();

        switch(algo) {
            case "mergesort":
                int[] arrM = randomArray(size);
                MergeSort.sort(arrM, metrics);
                break;

            case "quicksort":
                int[] arrQ = randomArray(size);
                QuickSort.sort(arrQ, metrics);
                break;

            case "select":
                int[] arrS = randomArray(size);
                int k = size / 2; // медиана
                metrics.enterRecursion();
                DeterministicSelect.select(arrS, 0, arrS.length - 1, k, metrics);
                metrics.exitRecursion();
                break;

            case "closest":
                ClosestPair.Point[] pts = new ClosestPair.Point[size];
                for(int i = 0; i < size; i++) pts[i] = new ClosestPair.Point(Math.random() * size, Math.random() * size);
                ClosestPair.closestPair(pts, metrics);
                break;

            default:
                System.out.println("Unknown algorithm: " + algo);
                return;
        }

        CSVWriter writer = new CSVWriter(output);
        writer.write(algo, size, metrics);

        System.out.println("Done: time=" + metrics.getTimeMs() +
                " ms, depth=" + metrics.getPeakDepth() +
                ", comparisons=" + metrics.getComparisons() +
                ", allocations=" + metrics.getAllocations());
    }

    private static int[] randomArray(int size) {
        int[] arr = new int[size];
        for(int i = 0; i < size; i++) arr[i] = (int)(Math.random() * size);
        return arr;
    }
}
