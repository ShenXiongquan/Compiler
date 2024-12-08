package backend.operand;

public class Label extends Operand {

    public Label(String name) {
        this.name = name;
    }

    @Override
    public String mips() {
        return name;
    }
}
