package frontend.symbol;


import frontend.ir.Value;
import frontend.ir.type.Type;
import frontend.node.FuncType;
import frontend.node.constInitVal.ConstInitVal;
import frontend.node.initVal.InitVal;

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
    public FuncType returnType;//函数返回值类型
    public int paramNum;//函数参数个数
    public final List<Symbol> paramList=new ArrayList<>();//函数参数列表

    public Symbol(){

    }

    public Symbol(int tableId,Value value,boolean isConst){
        this.tableId=tableId;
        this.value=value;
        this.isConst=isConst;
        this.token= value.getName();

    }
}

