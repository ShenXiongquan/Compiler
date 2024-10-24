package frontend.symbol;


import java.util.LinkedHashMap;

public class SymbolTable {
    public int id; 		// 当前符号表的id。
    public int fatherId; 	// 外层符号表的id。
    public LinkedHashMap<String, Symbol> directory = new LinkedHashMap<>();

    public SymbolTable(int id,int fatherId){
        this.id=id;
        this.fatherId=fatherId;
    }
}

