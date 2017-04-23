package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.FunctionalUnitManager;

// Issue — decode instructions & check for structural hazards
// Wait conditions: (1) the required FU is free; (2) no other instruction writes to the same register destination (to avoid WAW)
// Actions: (1) the instruction proceeds to the FU; (2) scoreboard updates its internal data structure
// If an instruction is stalled at this stage, no other instructions can proceed

public class IssueStage {
	public static int prev_inst_index = -1;
	public static int curr_inst_index = -1;

	public static void execute() throws Exception {
		if(FetchStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(FetchStage.prev_inst_index);
			
			if(canIssue(inst)){
				curr_inst_index = FetchStage.prev_inst_index;
				System.out.println("Issue- " + curr_inst_index + " - " + inst.toString());
				FetchUnit.i.setBusy(false);
				IssueUnit.i.setBusy(true);
				inst.markRegisterStatus();

				prev_inst_index = curr_inst_index;
				curr_inst_index++;
				IssueUnit.i.execute(inst);								
			}else{
				// initiate stall
				prev_inst_index = -1;
			}		
		}else{
			// relay stall ahead
			prev_inst_index = -1;
		}
	}

	private static boolean canIssue(Instruction inst) throws Exception {
		if(IssueUnit.i.isBusy()) return false;
		
		if(!FunctionalUnitManager.isUnitAvailable(inst)){  // check structural hazards
			System.out.println("structural hazard");
			return false;
		}
		
		if(inst.isDestinationBeingWritten()){ // check WAW hazards
			System.out.println("WAW hazard");
			return false;
		}
		
		return true;
	}
}
