import java.io.*;
import java.lang.System.Logger;
import java.util.*;

public class XSort {
    public static MrLogger logger = new MrLogger(Logger.Level.WARNING);

    public static void main(String[] args) throws IOException {
        // Validate command-line arguments
        for (String arg : args) {
            if (arg.equals("-v")) {
                logger = new MrLogger(Logger.Level.INFO);
            } else if (arg.equals("-vv")) {
                logger = new MrLogger(Logger.Level.DEBUG);
            } else if (arg.equals("-vvv")) {
                logger = new MrLogger(Logger.Level.TRACE);
            }
            logger.log(Logger.Level.INFO, "XSort version 1.0");
        }
        // remove the -v flag
        args = Arrays.stream(args).filter(arg -> !arg.equals("-v")).toArray(String[]::new);

        if (args.length < 1 || args.length > 3) {
            logger.log(Logger.Level.ERROR, "Usage: java XSort <run size> [merge type (2 or 4)] <options>");
            return;
        }

        if (args.length < 1) {
            logger.log(Logger.Level.ERROR, "Run size must be between 64 and 1024.");
            return;
        }

        int runSize;
        try {
            runSize = Integer.parseInt(args[0]);
            if (runSize < 64 || runSize > 1024) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            logger.log(Logger.Level.ERROR, "Run size must be between 64 and 1024.");
            return;
        }

        // Step 1: Read input and create initial runs
        List<File> sortedRuns = InitialRunGenerator.createSortedRuns(System.in, runSize);

        logger.log(Logger.Level.DEBUG, "Number of initial runs: " + sortedRuns.size());

        // If no merge type is provided, stop here
        if (args.length == 1) {
            logger.log(Logger.Level.WARNING, "No merge type provided. Printing sorted runs");
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
            logger.log(Logger.Level.ERROR, "Merge type must be 2 or 4.");
            return;
        }

        // Perform balanced k-way merge
        BalancedMerge.sort(sortedRuns, mergeType);
    }

    protected static class MrLogger {
        private Logger.Level minLevel;
        private static MrLogger instance;

        public MrLogger(Logger.Level level) {
            instance = this;
            minLevel = level;
        }

        public static MrLogger getInstance() {
            return instance;
        }

        private boolean canLog(Logger.Level level) {
            return level.compareTo(minLevel) >= 0;
        }

        public void log(Logger.Level level, String message) {
            if (canLog(level)) {
                System.err.println(level + ": " + message);
            }
        }
    }
}
