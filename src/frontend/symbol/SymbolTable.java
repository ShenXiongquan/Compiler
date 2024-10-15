package frontend.symbol;

import java.util.HashMap;

public class SymbolTable {
    public int id; 		// 当前符号表的id。
    public int fatherId; 	// 外层符号表的id。
    public HashMap<String, Symbol> directory = new HashMap<>();
}

