package frontend.llvm_ir;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * llvm中的编译单元，树的唯一根节点，一个model由多个GlobalValue组成。
 */
public class Model {
    private int strNum = 5;
    /**
     * model中的GlobalValue
     */
    private final LinkedList<GlobalValue> globalValues = new LinkedList<>();

    public void addGlobalValue(GlobalValue globalValue) {
        globalValues.add(globalValue);
    }

    public void addGlobalStr(GlobalValue globalValue) {
        globalValues.add(strNum++, globalValue);
    }

    public Function getint() {
        return (Function) globalValues.get(0);
    }

    public Function getchar() {
        return (Function) globalValues.get(1);
    }

    public Function putint() {
        return (Function) globalValues.get(2);
    }

    public Function putchar() {
        return (Function) globalValues.get(3);
    }

    public Function putstr() {
        return (Function) globalValues.get(4);
    }

    public String ir() {
        StringBuilder sb = new StringBuilder();
        for (GlobalValue globalValue : globalValues) {
            sb.append(globalValue.ir()).append('\n');
        }
        return sb.toString();
    }

    public List<Function> getFunctions() {
        List<Function> functions = new ArrayList<>();
        for (GlobalValue gv : globalValues) {
            if (gv instanceof Function) {
                functions.add((Function) gv);
            }
        }
        return functions;
    }

    public LinkedList<GlobalValue> getGlobalValues() {
        return globalValues;
    }
}
