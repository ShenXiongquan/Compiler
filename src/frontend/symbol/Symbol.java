package frontend.symbol;

import java.util.ArrayList;
import java.util.List;

public class Symbol {
    public int tableId; 	// 当前单词所在的符号表编号。

    public String token; 	// 当前单词所对应的字符串。

    public SymbolType symbolType;
    public int paramNum;
    public final List<SymbolType> paramTypeList=new ArrayList<>();

}
