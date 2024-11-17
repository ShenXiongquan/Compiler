package frontend.node;

import frontend.Visitor;
import frontend.token.token;
import frontend.tool.myWriter;

public class Character extends node {
    public token charConst;

    public void print(){
        charConst.print();
        myWriter.writeNonTerminal("Character");
    }

    @Override
    public void visit() {
        int c;
        String s= charConst.token().substring(1,charConst.token().length()-1);

        switch (s) {
            case "\\a": // 响铃
                c = 7;
                break;
            case "\\b": // 退格
                c = 8;
                break;
            case "\\t": // 制表符
                c = 9;
                break;
            case "\\n": // 换行
                c = 10;
                break;
            case "\\v": // 垂直制表符
                c = 11;
                break;
            case "\\f": // 换页
                c = 12;
                break;
            case "\\r": // 回车
                c = 13;
                break;
            case "\\\"": // 双引号
                c = 34;
                break;
            case "\\\'": // 单引号
                c = 39;
                break;
            case "\\\\": // 反斜杠
                c = 92;
                break;
            case "\\0": // 空字符
                c = 0;
                break;
            default:
                c=s.charAt(0);
        }
        Visitor.upConstValue=c;
    }
}//字符
