package Instructions;

import Instructions.Operands.*;
import Managers.MemoryManager;

public class LD extends Instruction implements Instructable, Memorable{
	RegisterOperand register_operand;
	MemoryOperand memory_operand;
	
	public LD(RegisterOperand ro, MemoryOperand mo) {
		this.register_operand = ro;
		this.memory_operand = mo;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write() throws Exception {
		double value = MemoryManager.read(memory_operand.final_address(), "double");
		register_operand.setValue(value);
	}

	
}