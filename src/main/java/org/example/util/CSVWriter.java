package org.example.util;

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private final String filename;

    public CSVWriter(String filename) {
        this.filename = filename;
    }

    public void write(String algo, int n, Metrics m) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(algo + "," + n + "," + m.getTimeMs() + "," +
                    m.getComparisons() + "," +
                    m.getAllocations() + "," +
                    m.getMaxDepth() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
