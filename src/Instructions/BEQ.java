package Instructions;

import java.util.ArrayList;

import Instructions.Operands.RegisterOperand;

public class BEQ extends Instruction{
	RegisterOperand register_operand1;
	RegisterOperand register_operand2;
	public String label;
	
	public BEQ(RegisterOperand register_operand1, RegisterOperand register_operand2, String label) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.label = label;
	}

	public boolean isConditionSatisfied() throws Exception {
		return register_operand1.getValue() == register_operand2.getValue();	
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub	
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