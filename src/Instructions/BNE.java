package Instructions;

import java.util.ArrayList;

import Instructions.Operands.ImmediateOperand;
import Instructions.Operands.MemoryOperand;
import Instructions.Operands.RegisterOperand;

public class BNE extends Instruction{
	RegisterOperand register_operand1;
	RegisterOperand register_operand2;
	public String label;
	
	public BNE(RegisterOperand register_operand1, RegisterOperand register_operand2, String label) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.label = label;
	}

	public boolean isConditionSatisfied() throws Exception {
		return register_operand1.getValue() != register_operand2.getValue();	
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RegisterOperand getDestinationRegister() throws Exception {
		return null;
	}

	@Override
	public ArrayList<RegisterOperand> getSourceRegisters() throws Exception {
		ArrayList<RegisterOperand> source_registers = new ArrayList<RegisterOperand>();
		source_registers.add(this.register_operand1);
		source_registers.add(this.register_operand2);
		return source_registers;
	}

	@Override
	public void write() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MemoryOperand getMemoryOperand() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmediateOperand getImmediateOperand() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}