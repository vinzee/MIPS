package Instructions.Operands;

public class ImmediateOperand extends Operand{
	public int value;
	
	public ImmediateOperand(String raw_value) {
		super();
		this.value = Integer.parseInt(raw_value);
	}
}
