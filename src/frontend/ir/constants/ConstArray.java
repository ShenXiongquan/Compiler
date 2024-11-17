package frontend.ir.constants;


import frontend.ir.type.ArrayType;
import java.util.ArrayList;
import java.util.List;

public class ConstArray extends Constant{
    /**
     * 常量数组的初始化
     */
    private List<ConstInt> ArrayElements=new ArrayList<>();

    public ConstArray(ArrayType arrayType,ArrayList<ConstInt> ArrayElements) {
        super(arrayType);
        this.ArrayElements.addAll(ArrayElements);
    }

    public List<ConstInt> getArrayElements() {
        return ArrayElements;
    }
    public ConstInt getArrayElement(int index){
        return ArrayElements.get(index);
    }
    @Override
    public String ir() {
        StringBuilder sb = new StringBuilder("[");
        int len=((ArrayType)getType()).getElementNum();
        for (int i = 0; i <len ; i++){
            sb.append(getArrayElement(i).getType().ir()).append(" ").append(getArrayElement(i).getName());
            if( i<len-1 )sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

}
