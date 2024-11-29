package frontend.symbol;


import java.util.ArrayList;

public class Symbol {
    private  int tableId; 	// 当前符号所在的符号表编号。

    private  String token; 	// 当前符号所对应的字符串。

    private boolean isConst=false;
    private boolean isArray=false;
    private final SymbolType symbolType; //当前symbol的类型
    private  ArrayList<Symbol> paramList;//函数参数列表

    public Symbol(int tableId,String token,boolean isConst,boolean isArray,SymbolType type){
        this.tableId=tableId;
        this.token=token;
        this.isConst=isConst;
        this.isArray=isArray;
        this.symbolType=type;
    }
    public Symbol(int tableId, String token, SymbolType type, ArrayList<Symbol> paramList){
        this.tableId=tableId;
        this.token=token;
        this.symbolType=type;
        this.paramList=paramList;
    }

    public Symbol(boolean isArray,SymbolType type){
        this.isArray=isArray;
        this.symbolType=type;
    }

    public boolean isConst() {
        return isConst;
    }

    public int getTableId() {
        return tableId;
    }
    public boolean isArray() {
        return isArray;
    }
    public String getToken() {
        return token;
    }

    public ArrayList<Symbol> getParamList() {
        return paramList;
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

}

