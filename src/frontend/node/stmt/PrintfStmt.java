package frontend.node.stmt;

import frontend.Visitor;
import frontend.ir.GlobalVariable;
import frontend.ir.Value;
import frontend.ir.constants.ConstInt;
import frontend.ir.constants.ConstStr;
import frontend.ir.instructions.MemInstructions.getelementptr;
import frontend.ir.instructions.OtherInstructions.call;
import frontend.ir.type.ArrayType;
import frontend.ir.type.IntegerType;
import frontend.node.Exp;
import frontend.token.token;
import frontend.tool.myWriter;

import java.util.ArrayList;
import java.util.List;

public class PrintfStmt extends Stmt {
    public token printf;
    public token stringConst;
    public final List<Exp> exps=new ArrayList<>();

    public token comma;

    public token lparent;
    public token rparent;
    public token semicn;

    @Override
    public void print() {
        printf.print();
        lparent.print();
        stringConst.print();
        if (!exps.isEmpty()) for (Exp exp : exps) {
            comma.print();
            exp.print();
        }
        if (rparent != null) rparent.print();
        if (semicn != null) semicn.print();
        myWriter.writeNonTerminal("Stmt");
    }
    @Override
    public void visit() {

        String rawString = stringConst.token().substring(1,stringConst.token().length()-1); // 获取字符串常量的值
        List<String> parts = new ArrayList<>();

        int lastIndex = 0;
        // 遍历 stringConst，找到占位符 (%d, %c)
        for (int i = 0; i < rawString.length(); i++) {
            if (rawString.charAt(i) == '%' && i + 1 < rawString.length()) {
                // 添加之前的普通字符串部分
                if (lastIndex < i) {
                    parts.add(rawString.substring(lastIndex, i));
                }
                // 添加占位符
                parts.add("%" + rawString.charAt(i + 1));
                i++; // 跳过占位符
                lastIndex = i + 1;
            }
        }
        // 添加剩余的字符串部分
        if (lastIndex < rawString.length()) {
            parts.add(rawString.substring(lastIndex));
        }

        for (int i=0,j=0;i< parts.size();i++) {
            String constStr=parts.get(i);
            if(constStr.equals("%d")){
                exps.get(j++).visit();
                Value output=zext(Visitor.upValue);
                call call=new call(Visitor.model.putint(),output);
                Visitor.curBlock.addInstruction(call);
            } else if (constStr.equals("%c")) {
                exps.get(j++).visit();
                Value output=zext(Visitor.upValue);
                call call=new call(Visitor.model.putchar(),output);
                Visitor.curBlock.addInstruction(call);
            }else {
                int len=constStr.length();
                for(int t=0;t<len;t++){
                    if(constStr.charAt(t)=='\\'){
                        len--;t++;
                    }
                }
                GlobalVariable printStr=new GlobalVariable(new ConstStr(new ArrayType(IntegerType.i8,len+1),constStr));
                Visitor.model.addGlobalStr(printStr);
                getelementptr getelementptr=new getelementptr(printStr, ConstInt.zeroI64,ConstInt.zeroI64);
                Visitor.curBlock.addInstruction(getelementptr);
                call call=new call(Visitor.model.putstr(),getelementptr);
                Visitor.curBlock.addInstruction(call);
            }

        }
    }
}
