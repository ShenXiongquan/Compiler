package frontend.tool;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class errorManager {

    private static boolean hasError=false;
    private static final String errorFilePath = "error.txt";
    private static final FileWriter errorWriter;
    private static final TreeMap<Integer, String> errorMap = new TreeMap<>();

    static {
        try {
            errorWriter = new FileWriter(errorFilePath);
            errorWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void handleError(int tokenLine, String errorType) {
        hasError=true;
        errorMap.put(tokenLine, errorType);
    }

    public static boolean HasError() {
        return hasError;
    }

    public static void write() {
        for (Map.Entry<Integer, String> entry : errorMap.entrySet()) {
            try {
                errorWriter.write(entry.getKey() + " " + entry.getValue() + "\n");
                errorWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void close() {
        try {
            errorWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
