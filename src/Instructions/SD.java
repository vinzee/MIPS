package Instructions;

import Instructions.Operands.*;
import Managers.MemoryManager;

public class SD extends Instruction implements Instructable, Memorable{
	RegisterOperand register_operand;
	MemoryOperand memory_operand;
	
	public SD(RegisterOperand ro, MemoryOperand mo) {
		this.register_operand = ro;
		this.memory_operand = mo;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write() throws Exception {
		MemoryManager.write(memory_operand.final_address(), "double", (int) register_operand.getValue());
	}
}