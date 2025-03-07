import java.io.*;
import java.util.*;

public class BalancedMerge {
    public static void sort(List<File> runs, int mergeType) throws IOException {
        while (runs.size() > 1) {
            List<File> mergedRuns = new ArrayList<>();

            for (int i = 0; i < runs.size(); i += mergeType) {
                List<File> subList = runs.subList(i, Math.min(i + mergeType, runs.size()));
                File mergedFile = mergeFiles(subList);
                mergedRuns.add(mergedFile);
            }

            runs = mergedRuns;
        }

        // Output final sorted result to System.out
        printToStdOut(runs.get(0));
    }

    private static File mergeFiles(List<File> inputFiles) throws IOException {
        PriorityQueue<FileEntry> minHeap = new PriorityQueue<>();
        List<BufferedReader> readers = new ArrayList<>();

        File tempFile = File.createTempFile("mergedRun", ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (File file : inputFiles) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                readers.add(reader);
                String line = reader.readLine();
                if (line != null) {
                    minHeap.add(new FileEntry(line, reader));
                }
            }

            while (!minHeap.isEmpty()) {
                FileEntry entry = minHeap.poll();
                writer.write(entry.line);
                writer.newLine();

                String nextLine = entry.reader.readLine();
                if (nextLine != null) {
                    minHeap.add(new FileEntry(nextLine, entry.reader));
                }
            }
        }

        for (BufferedReader reader : readers) {
            reader.close();
        }

        return tempFile;
    }

    private static void printToStdOut(File sortedFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(sortedFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    private static class FileEntry implements Comparable<FileEntry> {
        String line;
        BufferedReader reader;

        public FileEntry(String line, BufferedReader reader) {
            this.line = line;
            this.reader = reader;
        }

        @Override
        public int compareTo(FileEntry other) {
            return this.line.compareTo(other.line);
        }
    }
}
