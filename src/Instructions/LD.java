package Instructions;

import Instructions.Operands.*;

public class LD extends Instruction implements Instructable{
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

}