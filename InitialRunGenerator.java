// Alexander Trotter 1644272, Eli Murray 1626960

import java.io.*;
import java.lang.System.Logger;
import java.util.*;

public class InitialRunGenerator {
    private static XSort.MrLogger logger = XSort.MrLogger.getInstance();

    /**
     * Reads input lines in chunks (runs) of given size, sorts each run, and writes to temporary files.
     * @param input The input stream from which lines are read.
     * @param runSize The size of each run (chunk) to sort.
     * @return List of temporary files containing sorted runs.
     * @throws IOException If an I/O error occurs.
     */
    public static List<File> createSortedRuns(InputStream input, int runSize) throws IOException {
        logger.log(Logger.Level.DEBUG, "Creating sorted runs with run size " + runSize);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        List<File> sortedRuns = new ArrayList<>();
        List<String> buffer = new ArrayList<>(runSize);

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            buffer.add(line);
            if (buffer.size() >= runSize) {
                sortedRuns.add(writeSortedRun(buffer));
                buffer.clear();
            }
        }
        if (!buffer.isEmpty()) {
            sortedRuns.add(writeSortedRun(buffer));
        }
        return sortedRuns;
    }

    /**
     * Sorts the provided buffer using heapsort and writes it to a temporary file.
     * @param buffer List of strings to sort and write.
     * @return A temporary file containing the sorted lines.
     * @throws IOException If an I/O error occurs.
     */
    private static File writeSortedRun(List<String> buffer) throws IOException {
        logger.log(Logger.Level.DEBUG, "Writing sorted run with " + buffer.size() + " records");
        heapsort(buffer);
        File tempFile = File.createTempFile("sortedRun", ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (String line : buffer) {
                writer.write(line);
                writer.newLine();
            }
        }
        return tempFile;
    }

    /**
     * Sorts a list of strings using the heapsort algorithm.
     * @param arr List of strings to be sorted.
     */
    private static void heapsort(List<String> arr) {
        logger.log(Logger.Level.DEBUG, "Sorting " + arr.size() + " records using heapsort");
        int n = arr.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            Collections.swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    /**
     * Maintains the heap property for the provided index in the array.
     * @param arr List of strings representing the heap.
     * @param n Size of the heap.
     * @param i Index to heapify.
     */
    private static void heapify(List<String> arr, int n, int i) {
        logger.log(Logger.Level.TRACE, "Heapifying at index " + i);
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;
        if (left < n && arr.get(left).compareTo(arr.get(largest)) > 0)
            largest = left;
        if (right < n && arr.get(right).compareTo(arr.get(largest)) > 0)
            largest = right;
        if (largest != i) {
            Collections.swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }
}
