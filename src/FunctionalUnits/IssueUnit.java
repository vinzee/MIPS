package FunctionalUnits;

import Instructions.*;
import MIPS.MIPS;
import Stages.FetchStage;

public class IssueUnit extends FunctionalUnit {
	public static final IssueUnit i = new IssueUnit(1);
	
	private IssueUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst) {
		if(inst instanceof HLT){
			MIPS.halt_machine();
		}else if(inst instanceof J){
			int goto_index = MIPS.label_map.get(((J) inst).label);
			FetchStage.curr_inst_index = goto_index;
		}
	}
}
