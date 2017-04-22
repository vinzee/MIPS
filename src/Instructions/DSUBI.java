package Instructions;

import Instructions.Operands.*;
import Managers.RegisterManager;

public class DSUBI extends Instruction implements Instructable{
	RegisterOperand register_operand1;
	RegisterOperand register_operand2;
	ImmediateOperand immediate_operand;
	
	public DSUBI(RegisterOperand register_operand1, RegisterOperand register_operand2, ImmediateOperand immediate_operand) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.immediate_operand = immediate_operand;
	}

	@Override
	public void execute() throws Exception {
		double value = RegisterManager.read(this.register_operand2) - this.immediate_operand.value;
		RegisterManager.write(this.register_operand1, value);
	}

}