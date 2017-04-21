package Instructions;

import Instructions.Operands.*;

public class ORI extends Instruction implements Instructable{
	RegisterOperand register_operand1;
	RegisterOperand register_operand2;
	ImmediateOperand immediate_operand;
	
	public ORI(RegisterOperand register_operand1, RegisterOperand register_operand2, ImmediateOperand immediate_operand) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.immediate_operand = immediate_operand;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}