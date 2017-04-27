package FunctionalUnits;

import Instructions.*;
import MIPS.MIPS;
import Stages.*;

public class ReadOperandUnit extends FunctionalUnit {
	public static final ReadOperandUnit i = new ReadOperandUnit(1);
	
	private ReadOperandUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst) throws Exception {
		if((inst instanceof BEQ) && ((BEQ)inst).isConditionSatisfied()){
			FetchStage.id = MIPS.label_map.get(((BNE) inst).label);
			IssueStage.gid_queue.add(-1);
			ReadOperandsStage.gid_queue.add(-1);
		}else if((inst instanceof BNE) && ((BNE)inst).isConditionSatisfied()){
			FetchStage.id = MIPS.label_map.get(((BNE) inst).label);
			IssueStage.gid_queue.add(-1);
			ReadOperandsStage.gid_queue.add(-1);
		}
	}
}
