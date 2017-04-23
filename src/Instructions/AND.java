package Instructions;

import java.util.ArrayList;

import Instructions.Operands.RegisterOperand;
import Managers.RegisterManager;

public class AND extends Instruction implements Executable{
	RegisterOperand register_operand1;
	RegisterOperand register_operand2;
	RegisterOperand register_operand3;

	public AND(RegisterOperand register_operand1, RegisterOperand register_operand2, RegisterOperand register_operand3) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.register_operand3 = register_operand3;
	}
	
	@Override
	public void execute() throws Exception {
		double value = (int) RegisterManager.read(this.register_operand2) & (int) RegisterManager.read(this.register_operand3);
		RegisterManager.write(this.register_operand1, value);
	}

	@Override
	public RegisterOperand getDestinationRegister() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<RegisterOperand> getSourceRegisters() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}