package Instructions;

import java.util.ArrayList;

import Instructions.Operands.RegisterOperand;
import Managers.RegisterManager;

public class DSUB extends Instruction implements Executable{
	RegisterOperand register_operand1;
	RegisterOperand register_operand2;
	RegisterOperand register_operand3;

	public DSUB(RegisterOperand register_operand1, RegisterOperand register_operand2, RegisterOperand register_operand3) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.register_operand3 = register_operand3;
	}
	
	@Override
	public void execute() throws Exception {
		double value = RegisterManager.read(this.register_operand2) - RegisterManager.read(this.register_operand3);
		RegisterManager.write(this.register_operand1, value);
	}

	@Override
	public RegisterOperand getDestinationRegister() throws Exception {
		return this.register_operand1;
	}

	@Override
	public ArrayList<RegisterOperand> getSourceRegisters() throws Exception {
		ArrayList<RegisterOperand> source_registers = new ArrayList<RegisterOperand>();
		source_registers.add(this.register_operand2);
		source_registers.add(this.register_operand3);
		return source_registers;
	}

}