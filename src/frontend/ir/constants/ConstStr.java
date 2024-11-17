package frontend.ir.constants;

import frontend.ir.type.ArrayType;
import frontend.ir.type.IntegerType;

public class ConstStr extends Constant{

    private final String str;

    public ConstStr(String str,int elementNum){
        super(new ArrayType(new IntegerType(8),elementNum));
        StringBuilder paddedStr = new StringBuilder(str);
        for (int i = str.length(); i < elementNum-1; i++) {
            paddedStr.append("\\00");
        }
        // 保存处理后的字符串
        this.str = paddedStr.toString();
    }
    @Override
    public String ir() {
        return "c\"" + str + "\\00\"";
    }

    @Override
    public String getName() {
        return ir();
    }
}
