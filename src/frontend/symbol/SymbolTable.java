package frontend.symbol;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SymbolTable {

    /**
     * 每个符号表唯一标识符，自增
     */
    private static int globalId = 0;
    /**
     * 指向外层符号表的指针
     */
    public SymbolTable pre;
    /**
     * 指向内层符号表的指针
     */
    public final List<SymbolTable> next;
    public final int id; // 当前符号表的id。

    public final LinkedHashMap<String, Symbol> directory = new LinkedHashMap<>();

    public SymbolTable(){
        this.id=++globalId;
        this.next = new ArrayList<>();
        this.pre = null;
    }

    public SymbolTable pushScope() {
        SymbolTable newTable= new SymbolTable();
        newTable.pre = this;
        this.next.add(newTable);
        return newTable;
    }
    public SymbolTable popScope() {
        return this.pre;
    }

// 添加新符号到符号表中
    public boolean addSymbol(Symbol symbol) {
        if (existInScope(symbol.getToken())) {
            return false;
        }
        directory.put(symbol.getToken(), symbol);
        return true;
    }

//查询是否重定义
    public boolean existInScope(String token) {
        return directory.containsKey(token);
    }

    /**
     * 查找符号是否定义
     * @param token 符号token
     * @return  symbol
     */
    public Symbol getSymbol(String token) {
        Symbol symbol = directory.get(token);
        if (symbol != null) {
            return symbol;
        }
        // 如果当前作用域没有，向外层符号表查找
        if (pre != null) {
            return pre.getSymbol(token);
        }

        return null;
    }

}

