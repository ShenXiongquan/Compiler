package frontend.llvm_ir;


import java.util.LinkedList;

/**
 * llvm中的编译单元，树的唯一根节点，一个model由多个GlobalValue组成。
 */
public class Model {
    /**
     * model中的GlobalValue
     */
    private static int strNum=5;
    private final LinkedList<GlobalValue> globalValues=new LinkedList<>();

    public void addGlobalValue(GlobalValue globalValue){
        globalValues.add(globalValue);
    }
    public void addGlobalStr(GlobalValue globalValue){
        globalValues.add(strNum++,globalValue);
    }
    public Function getint(){
      return (Function) globalValues.get(0);
    }

    public Function getchar(){
        return (Function) globalValues.get(1);
    }

    public Function putint(){
        return (Function) globalValues.get(2);
    }
    public Function putchar(){
        return (Function) globalValues.get(3);
    }
    public Function putstr(){
        return (Function) globalValues.get(4);
    }
    public String ir(){
        StringBuilder sb = new StringBuilder();
        for(GlobalValue globalValue:globalValues){
            sb.append(globalValue.ir()).append('\n');
        }
        return sb.toString();
    }

}
