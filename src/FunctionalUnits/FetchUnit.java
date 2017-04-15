package FunctionalUnits;

import Instructions.Instruction;

public class FetchUnit extends FunctionalUnit {
	public static final FetchUnit i = new FetchUnit(1);
	
	private FetchUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst) {
		// TODO Auto-generated method stub
		
	}
}
