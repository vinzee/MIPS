package Instructions;

import Instructions.Operands.*;

public class LUI extends Instruction implements Instructable, Memorable{
	RegisterOperand register_operand;
	ImmediateOperand immediate_operand;
	
	public LUI(RegisterOperand ro, ImmediateOperand io) {
		this.register_operand = ro;
		this.immediate_operand = io;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write() throws Exception {
		register_operand.setValue(immediate_operand.value);
	}

}