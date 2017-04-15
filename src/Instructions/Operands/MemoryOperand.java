package Instructions.Operands;

public class MemoryOperand extends Operand {
	int address;
	
	public MemoryOperand(String raw_value) {
		super();
		this.address = Integer.parseInt(raw_value);
	}
}
