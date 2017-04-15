package Instructions;

import Instructions.Operands.Operand;

public class LI extends Instruction implements Instructable{
	Operand operand1;
	Operand operand2;
	
	public LI(String[] operands) {
		this.operands = operands;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}