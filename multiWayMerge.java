import java.io.*;
import java.util.*;

public class multiWayMerge {
    public static File mergeSortedFiles(List<File> files, String outputFileName) throws IOException {
        File mergedFile = new File(outputFileName); // Use provided output file name
    
        
        PriorityQueue<FileEntry> minHeap = new PriorityQueue<>();
        List<BufferedReader> readers = new ArrayList<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mergedFile))) {
            // Open each file and add the first line to the priority queue
            for (File file : files) {
                System.out.println("Merging file: " + file.getAbsolutePath() + " (exists: " + file.exists() + ")");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                readers.add(reader);
                String line = reader.readLine();
                if (line != null) {
                    minHeap.add(new FileEntry(line, reader));
                }
            }

            // Merge files
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

        // Close all readers
        for (BufferedReader reader : readers) {
            reader.close();
        }

        return mergedFile;
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
