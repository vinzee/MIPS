package FunctionalUnits;

import Instructions.*;
import MIPS.MIPS;

public class IssueUnit extends FunctionalUnit {
	public static final IssueUnit i = new IssueUnit(1);
	
	private IssueUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst) {
		if(inst instanceof HLT){
			MIPS.halt_machine();
		}
	}
}
