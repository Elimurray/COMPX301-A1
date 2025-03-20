// Alexander Trotter 1644272, Eli Murray 1626960

import java.io.*;
import java.lang.System.Logger;
import java.util.*;

/**
 * XSort is the main class for performing external sort on large input using run generation and balanced merge.
 */
public class XSort {
    public static MrLogger logger = new MrLogger(Logger.Level.WARNING);

    /**
     * Main entry point for the XSort program.
     * @param args Command-line arguments: <run size> [merge type (2 or 4)] <options (-v, -vv, -vvv)>
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        // Validate command-line arguments
        for (String arg : args) {
            switch (arg) {
                case "-v" -> logger = new MrLogger(Logger.Level.INFO);
                case "-vv" -> logger = new MrLogger(Logger.Level.DEBUG);
                case "-vvv" -> logger = new MrLogger(Logger.Level.TRACE);
                default -> {
                }
            }
            logger.log(Logger.Level.INFO, "XSort version 1.0");
        }

        args = Arrays.stream(args).filter(arg -> !arg.equals("-v")).toArray(String[]::new);

        if (args.length < 1 || args.length > 3) {
            logger.log(Logger.Level.ERROR, "Usage: java XSort <run size> [merge type (2 or 4)] <options>");
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

        // If no merge type is provided, just print the runs
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

        // Step 2: Perform balanced k-way merge
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

        BalancedMerge.sort(sortedRuns, mergeType);
    }

    /**
     * Custom logger implementation that logs based on verbosity level.
     */
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

        /**
         * Logs a message to standard error if the level is within the minimum threshold.
         * @param level Logging level of the message.
         * @param message Message to log.
         */
        public void log(Logger.Level level, String message) {
            if (canLog(level)) {
                System.err.println(level + ": " + message);
            }
        }
    }
}
