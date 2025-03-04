// import java.io.*;
// import java.util.*;

// public class fourWayMergeSort {
//     public static File mergeAllRuns(List<File> runs, String outputFileName) throws IOException {

//         File output = new File(outputFileName);

//         BufferedWriter writer = new BufferedWriter(new FileWriter(output));
//         for (File file : runs) {
//             BufferedReader reader = new BufferedReader(new FileReader(file));

//             String line = reader.readLine();
//             while (line != null) {
//                 writer.write(line);
//                 writer.newLine();
//                 line = reader.readLine();
//             }

//             reader.close();
//         }
//         writer.close();

//         return output;
//     }

//     public static List<File> splitAndSort(File input) {
//         List<File> output = new ArrayList<>();
//         List<String> lines = new ArrayList<>();

        
//         try {
//             BufferedReader reader = new BufferedReader(new FileReader(input));
//             String line = reader.readLine(); 
//             while (line != null) {
//                 lines.add(line);
//                 line = reader.readLine();
//             }
//             reader.close();

//             int count = lines.size() / 4;
//             // Collections.sort(lines);

//             for (int i = 0; i < 4; i++) {
//                 File tempFile = File.createTempFile("sortedRun" + i, ".txt");
//                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
//                 tempFile.deleteOnExit();

//                 for (int j = 0 + i * count; j < count + i * count; j++) {
//                     if (i == 3) {
//                         for (int k = j; k < lines.size(); k++) {
//                             writer.write(lines.get(k));
//                             writer.newLine();
//                             System.out.println(lines.get(k));
//                         }
//                         break;
//                     }

//                     writer.write(lines.get(j));
//                     writer.newLine();
//                     System.out.println(lines.get(j));
//                 }

//                 writer.close();

//                 output.add(tempFile);

//             }



//         } catch (Exception e) {
//         }
//         return output;
        

//     }
// }


import java.io.*;
import java.util.*;

public class fourWayMergeSort {
    public static List<File> splitAndSort(File input) throws IOException {
        List<File> output = new ArrayList<>();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // Sort the entire list
        Collections.sort(lines);

        // Divide into 4 parts
        int count = lines.size() / 4;
        for (int i = 0; i < 4; i++) {
            File tempFile = File.createTempFile("sortedRun" + i, ".txt");
            tempFile.deleteOnExit();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                for (int j = i * count; j < ((i == 3) ? lines.size() : (i + 1) * count); j++) {
                    writer.write(lines.get(j));
                    writer.newLine();
                }
            }

            output.add(tempFile);
        }

        return output;
    }

    public static File mergeAllRuns(List<File> runs, String outputFileName) throws IOException {
        System.out.println("Merging runs into: " + outputFileName);
        return multiWayMerge.mergeSortedFiles(runs, outputFileName);
    }
    
}
