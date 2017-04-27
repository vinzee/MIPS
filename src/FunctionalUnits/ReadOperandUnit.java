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
		if((inst instanceof BEQ) && ((BEQ)inst).isConditionSatisfied()){
			FetchStage.id = MIPS.label_map.get(((BNE) inst).label);
//			IssueStage.gid_queue.poll();
		}else if((inst instanceof BNE) && ((BNE)inst).isConditionSatisfied()){
			FetchStage.id = MIPS.label_map.get(((BNE) inst).label.toUpperCase());
//			IssueStage.gid_queue.poll();sssss
		}else{
			ExecuteStage.gid_queue.add(gid);
		}
	}
}
