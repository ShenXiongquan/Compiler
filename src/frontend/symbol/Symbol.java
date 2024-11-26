package frontend.symbol;


import frontend.llvm_ir.Visitor;
import frontend.llvm_ir.Value;

import java.util.ArrayList;
import java.util.List;

public class Symbol {
    public int tableId; 	// 当前符号所在的符号表编号。

    public String token; 	// 当前符号所对应的字符串。

    public Value value;
    public SymbolType symbolType; //当前symbol的类型
    //是否是常量
    public boolean isConst;

    //函数符号相关信息
    public int paramNum;//函数参数个数
    public final List<Symbol> paramList=new ArrayList<>();//函数参数列表

}

