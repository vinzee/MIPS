package Instructions.Operands;

public class ImmediateOperand extends Operand{
	public double value;

	public ImmediateOperand(String raw_value) {
		super();
		try{
			this.value = Float.parseFloat(raw_value);
		}catch(NumberFormatException e){
			throw new Error("Invalid Immediate Operand: " + raw_value);
		}
	}

	@Override
	public String toString() {
		return Double.toString(value);
	}
}
