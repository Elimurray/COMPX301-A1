import java.io.*;
import java.util.*;

public class XSort {
    public static void main(String[] args) throws IOException {
        // Validate command-line arguments
        if (args.length < 1 || args.length > 2) {
            System.err.println("Usage: java XSort <run size> [merge type (2 or 4)]");
            return;
        }

        int runSize;
        try {
            runSize = Integer.parseInt(args[0]);
            if (runSize < 64 || runSize > 1024) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.err.println("Error: Run size must be between 64 and 1024.");
            return;
        }

        // Step 1: Read input and create initial runs
        List<File> sortedRuns = InitialRunGenerator.createSortedRuns(System.in, runSize);

        System.err.println(sortedRuns.size());


        // If no merge type is provided, stop here
        if (args.length == 1) {

            for (File file : sortedRuns) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null) {    
                    System.out.println(line);
                    line = reader.readLine();
                }
                System.out.println("\n----\n");
                reader.close();
            }

            return;
        }

        // Step 2: Perform a balanced 2-way or 4-way merge
        int mergeType;
        try {
            mergeType = Integer.parseInt(args[1]);
            if (mergeType != 2 && mergeType != 4) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.err.println("Error: Merge type must be 2 or 4.");
            return;
        }

        // Perform balanced k-way merge
        BalancedMerge.sort(sortedRuns, mergeType);
    }
}
