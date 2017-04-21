package Instructions;

import Instructions.Operands.RegisterOperand;

public class BNE extends Instruction implements Instructable{
	RegisterOperand register_operand1;
	RegisterOperand register_operand2;
	String label;
	
	public BNE(RegisterOperand register_operand1, RegisterOperand register_operand2, String label) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.label = label;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}