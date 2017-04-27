package FunctionalUnits;

import Instructions.*;
import MIPS.MIPS;
import Stages.*;

public class IssueUnit extends FunctionalUnit {
	public static final IssueUnit i = new IssueUnit(1);
	
	private IssueUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst, int gid) {
		if(inst instanceof HLT){
			MIPS.halt_machine();
		}else if(inst instanceof J){
			FetchStage.id = MIPS.label_map.get(((J) inst).label);
		}else{
			ReadOperandsStage.gid_queue.add(gid);			
		}
	}
}
