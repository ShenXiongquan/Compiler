package frontend.tool;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class errorManager {
    private static final String errorFilePath = "error.txt";
    private static final FileWriter errorWriter;

    private static final TreeMap<Integer, String> errorMap = new TreeMap<>();
    private static  boolean handleErrorFlag=true;
    static {
        try {
            errorWriter = new FileWriter(errorFilePath);
            errorWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setHandleErrorFlag(boolean handleErrorFlag) {
        errorManager.handleErrorFlag = handleErrorFlag;
    }

    public static boolean handleErrorSwitch(){
        return handleErrorFlag;
    }
    public static void handleError(int tokenLine, String errorType) {
        errorMap.put(tokenLine,errorType);

    }
    public static void write(){
        for(Map.Entry<Integer, String> entry : errorMap.entrySet()){
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
