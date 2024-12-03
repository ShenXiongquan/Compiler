package backend.MIPSInstructions;

import backend.operand.Operand;

public abstract class MIPSBinary extends MIPSInstruction {

    public final String type;

    public MIPSBinary(String type, Operand dst, Operand src1, Operand src2) {
        this.operand1 = dst;
        this.operand2 = src1;
        this.operand3 = src2;
        this.type = type;
    }

    public Operand getDst() {
        return operand1;
    }

    public Operand getSrc1() {
        return operand2;
    }

    public Operand getSrc2() {
        return operand3;
    }

    @Override
    public String mips() {
        StringBuilder sb = new StringBuilder();

        switch (type) {
            case "div" -> {
                sb.append("div ").append(getSrc1().mips()).append(", ").append(getSrc2().mips());
                sb.append("\nmflo ").append(getDst().mips());
            }
            case "rem" -> {
                sb.append("rem ").append(getSrc1().mips()).append(", ").append(getSrc2().mips());
                sb.append("\nmfhi ").append(getDst().mips());
            }
            default -> sb.append(type).append(" ")
                    .append(getDst().mips()).append(", ")
                    .append(getSrc1().mips()).append(", ")
                    .append(getSrc2().mips());
        }
        return sb.toString();
    }

}
