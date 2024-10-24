package frontend.symbol;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SymbolTable {
    private static int globalId = 0;

    public SymbolTable pre; // 指向外层符号表的指针

    public List<SymbolTable> next; // 指向内层符号表的指针
    public final int id; 		// 当前符号表的id。

    public final LinkedHashMap<String, Symbol> directory = new LinkedHashMap<>();

    public SymbolTable(){
        this.id=++globalId;
        this.next = new ArrayList<>();
        this.pre = null;
    }

    public Symbol getSymbol(String token){
        return directory.get(token);
    }


}

