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
				String label = (inst instanceof BEQ) ? ((BEQ) inst).label : ((BNE) inst).label;
				int jump_id = MIPS.label_map.get(label.toUpperCase());
				if(FetchStage.insts_processed_after_jump == 1){
					FetchStage.setId(-1); // set next fetch
				}else{
					MIPS.jump = true;
				}
				FetchStage.setNextId(jump_id); // set next fetch
				IssueStage.gid_queue.clear();
			}else{
				IssueStage.gid_queue.add(0, -1);
			}
			FetchStage.insts_processed_after_jump = 0;
			MIPS.pre_jump = false;
		}else{
			ExecuteStage.gid_queue.add(gid);
		}
	}
}
