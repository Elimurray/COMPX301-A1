// import java.io.*;
// import java.util.*;

// public class externalMergeSort {
//     public static void main(String[] args) throws IOException {
//         if (args.length == 0) {
//             System.out.println("Usage: java ExternalMergeSort <input-file>");
//             return;
//         }

//         File inputFile = new File(args[0]);
//         System.out.println("Input file path: " + inputFile.getAbsolutePath());

//         if (!inputFile.exists()) {
//             System.out.println("Error: Input file does not exist.");
//             return;
//         }

//         // Fix: Ensure output file is saved in the same directory
        
//         String outputFileName = "sorted_" + inputFile.getName();
//         System.out.println("Output file will be saved at: " + outputFileName);

//         List<File> sortedRuns = fourWayMergeSort.splitAndSort(inputFile);
//         System.out.println(sortedRuns.size());
//         File sortedFile = fourWayMergeSort.mergeAllRuns(sortedRuns, outputFileName);



//         System.out.println("Sorted file saved at: " + sortedFile.getAbsolutePath());
//     }
// }



import java.io.*;
import java.util.*;

public class externalMergeSort {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: java ExternalMergeSort <input-file>");
            return;
        }

        File inputFile = new File(args[0]);
        System.out.println("Input file path: " + inputFile.getAbsolutePath());

        if (!inputFile.exists()) {
            System.out.println("Error: Input file does not exist.");
            return;
        }

        String outputFileName = "sorted_" + inputFile.getName();
        System.out.println("Output file will be saved at: " + outputFileName);

        List<File> sortedRuns = fourWayMergeSort.splitAndSort(inputFile);
        System.out.println("Number of sorted runs: " + sortedRuns.size());

        File sortedFile = multiWayMerge.mergeSortedFiles(sortedRuns, outputFileName);
        System.out.println("Sorted file saved at: " + sortedFile.getAbsolutePath());
    }
}
