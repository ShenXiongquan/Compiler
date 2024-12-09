package frontend.tool;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class errorManager {
    private static boolean hasError = false;
    private static final String errorFilePath = "error.txt";
    private static final TreeMap<Integer, String> errorMap = new TreeMap<>();

    public static void handleError(int tokenLine, String errorType) {
        hasError = true;
        errorMap.put(tokenLine, errorType);
    }

    public static boolean HasError() {
        return hasError;
    }

    public static void write() {
        try (FileWriter errorWriter = new FileWriter(errorFilePath)) {
            for (Map.Entry<Integer, String> entry : errorMap.entrySet()) {
                String errorEntry = entry.getKey() + " " + entry.getValue();
                System.out.println(errorEntry);
                errorWriter.write(errorEntry + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write errors to the file", e);
        }
    }
}
