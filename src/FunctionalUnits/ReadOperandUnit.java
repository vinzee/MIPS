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
			IssueStage.curr_inst_index = -1;
			int goto_index = MIPS.label_map.get(((BEQ) inst).label);
			FetchStage.curr_inst_index = goto_index;
			ReadOperandsStage.prev_inst_index = ReadOperandsStage.curr_inst_index = -1;
		}else if((inst instanceof BNE) && ((BNE)inst).isConditionSatisfied()){
			IssueStage.curr_inst_index = -1;
			int goto_index = MIPS.label_map.get(((BNE) inst).label);
			FetchStage.curr_inst_index = goto_index;
			ReadOperandsStage.prev_inst_index = ReadOperandsStage.curr_inst_index = -1;
		}
	}
}
