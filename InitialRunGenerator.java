import java.io.*;
import java.util.*;

public class InitialRunGenerator {
    public static List<File> createSortedRuns(InputStream input, int runSize) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        List<File> sortedRuns = new ArrayList<>();
        List<String> buffer = new ArrayList<>(runSize);

        String line;
        while ((line = reader.readLine()) != null) {
            if(line.trim().isEmpty()) {
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

    private static File writeSortedRun(List<String> buffer) throws IOException {
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

    // Heapsort implementation
    private static void heapsort(List<String> arr) {
        int n = arr.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            Collections.swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static void heapify(List<String> arr, int n, int i) {
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;
        if (left < n && arr.get(left).compareTo(arr.get(largest)) > 0) largest = left;
        if (right < n && arr.get(right).compareTo(arr.get(largest)) > 0) largest = right;
        if (largest != i) {
            Collections.swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }
}
