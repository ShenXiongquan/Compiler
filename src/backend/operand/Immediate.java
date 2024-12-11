package backend.operand;

public class Immediate extends Operand {

    public static final Immediate ZERO = new Immediate(0);

    public static Immediate MINUS_FOUR = new Immediate(-4);
    private final int value;

    public Immediate(int value) {
        this.value = value;
    }

    @Override
    public String mips() {
        return String.valueOf(value);
    }


}

