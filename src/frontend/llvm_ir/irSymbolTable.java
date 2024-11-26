package frontend.llvm_ir;


import frontend.symbol.Symbol;

import java.util.HashMap;


public class irSymbolTable {
    private irSymbolTable pre;
    private final HashMap<String,Value> symbols=new HashMap<>();
    public irSymbolTable getPre() {
        return pre;
    }
    public void setPre(irSymbolTable pre) {
        this.pre = pre;
    }

    public void addSymbol(String symbol,Value value){
        symbols.put(symbol,value);
    }
    public Value getSymbolValue(String symbol){
        Value value=symbols.get(symbol);
        if (value!= null) return value;
        if (pre != null) return pre.getSymbolValue(symbol);
        return null;
    }
}
