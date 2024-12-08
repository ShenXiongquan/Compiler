package backend.operand;

public class Immediate extends Operand {

    public static Immediate zero = new Immediate(0);
    private final int value;

    public Immediate(int value) {
        this.value = value;
    }

    @Override
    public String mips() {
        return String.valueOf(value);
    }

    public int getValue() {
        return value;
    }
}

