package frontend.symbol;


import java.util.LinkedHashMap;

public class SymbolTable {
    public final int id; 		// 当前符号表的id。
    public final int fatherId; 	// 外层符号表的id。
    public final LinkedHashMap<String, Symbol> directory = new LinkedHashMap<>();

    public SymbolTable(int id,int fatherId){
        this.id=id;
        this.fatherId=fatherId;
    }
}

