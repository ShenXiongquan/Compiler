package frontend.symbol;

public enum SymbolType {

        CONST_CHAR("ConstChar"),
        CHAR_VAR("Char"),
        VOID_FUNC("VoidFunc"),

        CONST_INT("ConstInt"),
        INT_VAR("Int"),
        CHAR_FUNC("CharFunc"),
        CONST_CHAR_ARRAY("ConstCharArray"),
        CHAR_ARRAY("CharArray"),
        INT_FUNC("IntFunc"),
        CONST_INT_ARRAY("ConstIntArray"),
        INT_ARRAY("IntArray");
        private final String typeName;
        SymbolType(String typeName) {
            this.typeName = typeName;
        }
        public String getTypeName() {
            return typeName;
        }
}
