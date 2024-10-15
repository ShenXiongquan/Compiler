package frontend.tool;

import java.io.FileWriter;
import java.io.IOException;

public class errorManager {
    private static final String errorFilePath = "error.txt";
    private static final FileWriter errorWriter;
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
        return handleErrorFlag==true;
    }
    public static void handleError(int tokenLine, String errorType) {
        try {
            errorWriter.write(tokenLine + " " + errorType + "\n");
            errorWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
