package Instructions;

import Instructions.Operands.RegisterOperand;
import Managers.RegisterManager;

public class MULD extends Instruction implements Instructable{
	RegisterOperand register_operand1;
	RegisterOperand register_operand2;
	RegisterOperand register_operand3;

	public MULD(RegisterOperand register_operand1, RegisterOperand register_operand2, RegisterOperand register_operand3) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.register_operand3 = register_operand3;
	}
	
	@Override
	public void execute() throws Exception {
		double value = RegisterManager.read(this.register_operand2) * RegisterManager.read(this.register_operand3);
		RegisterManager.write(this.register_operand1, value);
	}

}