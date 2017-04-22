package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;

// Issue — decode instructions & check for structural hazards
// Wait conditions: (1) the required FU is free; (2) no other instruction writes to the same register destination (to avoid WAW)
// Actions: (1) the instruction proceeds to the FU; (2) scoreboard updates its internal data structure
// If an instruction is stalled at this stage, no other instructions can proceed

public class IssueStage {
	public static int prev_inst_index = -1;
	public static int curr_inst_index = -1;

	public static void execute() {
		if(FetchStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(FetchStage.prev_inst_index);
			
			if(canIssue(inst)){
				curr_inst_index = FetchStage.prev_inst_index;
				System.out.println("Issue- " + curr_inst_index + " - " + inst.toString());
				FetchUnit.i.setBusy(false);
				IssueUnit.i.setBusy(true);
				IssueUnit.i.execute(inst);
				
				prev_inst_index = curr_inst_index;
				curr_inst_index++;
			}		
		}else{
			prev_inst_index = -1;
		}
	}

	private static boolean canIssue(Instruction inst) {
		if(IssueUnit.i.isBusy()) return false;

		return true;
	}
}
