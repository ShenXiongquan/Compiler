package frontend.node;

import frontend.llvm_ir.Visitor;

/**
 * ConstExp -> AddExp 中使用的 ident 必须是普通常量，不包括数组元素、函数返回值；当然 ConstExp 中还可以使用数值常量，字符常量
 */
public class ConstExp extends node {
    public AddExp addExp;

    public String print() {
        return addExp.print() +
                "<ConstExp>\n";
    }


    public void visit() {
        Visitor.calAble = true;
        addExp.visit();
        Visitor.calAble = false;
    }
}
