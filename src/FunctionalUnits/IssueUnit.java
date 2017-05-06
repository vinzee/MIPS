package FunctionalUnits;

import Instructions.*;
import MIPS.MIPS;
import Stages.*;

public class IssueUnit extends FunctionalUnit {
	public static final IssueUnit i = new IssueUnit(1);

	private IssueUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst, int gid, int id) throws Exception {
		if(inst instanceof HLT){
			MIPS.halt_machine();
		}else if(inst instanceof J){
			FetchStage.setId(-1); // skip current fetch
			FetchStage.setNextId(MIPS.label_map.get(((J) inst).label.toUpperCase())); // set next fetch
		}else{
			ReadOperandsStage.gid_queue.add(gid);
			ExecutionUnit.allocate_unit(inst, id, gid);
		}
	}
}
