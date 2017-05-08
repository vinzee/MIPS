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
		if((inst instanceof BEQ || inst instanceof BNE)){
			if( ((inst instanceof BEQ) && ((BEQ)inst).isConditionSatisfied()) ||
				(((inst instanceof BNE) && ((BNE)inst).isConditionSatisfied())) ){
				int jump_id = MIPS.label_map.get(((BNE) inst).label.toUpperCase());
				if(FetchStage.halts_processed == 1){
					FetchStage.setId(-1); // set next fetch
					FetchStage.setNextId(jump_id); // set next fetch
				}else{
					FetchStage.setNextId(jump_id); // set next fetch
					MIPS.jump = true;
				}
				IssueStage.gid_queue.clear();
//				FetchUnit.i.setBusy(false);
				FetchStage.halts_processed = 0;
			}else{
				IssueStage.gid_queue.add(0, -1);
			}
		}else{
			ExecuteStage.gid_queue.add(gid);
		}
	}
}
