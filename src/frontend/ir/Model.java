package frontend.ir;


import java.util.LinkedList;

/**
 * llvm中的编译单元，树的唯一根节点，一个model由多个GlobalValue组成。
 */
public class Model {
    /**
     * model中的GlobalValue
     */
    private LinkedList<GlobalValue> globalValues=new LinkedList<>();


    public void addGlobalValue(GlobalValue globalValue){
        globalValues.add(globalValue);
    }

    public String ir(){
        StringBuilder sb = new StringBuilder();
        for(GlobalValue globalValue:globalValues){
            sb.append(globalValue.ir()).append('\n');
        }
        return sb.toString();
    }

}
