package FunctionalUnits;

import Instructions.Instruction;

public class ReadOperandUnit extends FunctionalUnit {
	public static final ReadOperandUnit i = new ReadOperandUnit(1);
	
	private ReadOperandUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst) {
		// TODO Auto-generated method stub
		
	}
}
