package FunctionalUnits;

import Instructions.*;
import MIPS.MIPS;
import Stages.*;

public class ReadOperandUnit extends FunctionalUnit {
	public static final ReadOperandUnit i = new ReadOperandUnit(1);

	private ReadOperandUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst, int gid) throws Exception {
		if((inst instanceof BEQ)){
			if(((BEQ)inst).isConditionSatisfied()){
//				FetchStage.setId(-1); // skip current fetch
				FetchStage.setNextId(MIPS.label_map.get(((BNE) inst).label)); // set next fetch
				IssueStage.gid_queue.clear();
			}else{
				IssueStage.gid_queue.add(0, -1);
			}
		}else if(inst instanceof BNE){
			if(((BNE)inst).isConditionSatisfied()){
//				FetchStage.setId(-1); // skip current fetch
				MIPS.jump = true;
				FetchStage.setNextId(MIPS.label_map.get(((BNE) inst).label.toUpperCase())); // set next fetch
				IssueStage.gid_queue.clear();
			}else{
				IssueStage.gid_queue.add(0, -1);
			}
		}else{
			ExecuteStage.gid_queue.add(gid);
		}
	}
}
