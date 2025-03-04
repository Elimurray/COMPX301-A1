import java.io.*;
import java.util.*;

public class main {
    private static final int CHUNK_SIZE = 1000; 

    
    public static List<File> splitAndSort(File inputFile) throws IOException {
        List<File> sortedRuns = new ArrayList<>();
        List<String> buffer = new ArrayList<>(CHUNK_SIZE);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.add(line);
                if (buffer.size() >= CHUNK_SIZE) {
                    sortedRuns.add(writeSortedRun(buffer));
                    buffer.clear();
                }
            }
            if (!buffer.isEmpty()) {
                sortedRuns.add(writeSortedRun(buffer));
            }
        }
        return sortedRuns;
    }

    private static File writeSortedRun(List<String> buffer) throws IOException {
        Collections.sort(buffer);
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
}
