package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

// Issue — decode instructions & check for structural hazards
// Wait conditions: (1) the required FU is free; (2) no other instruction writes to the same register destination (to avoid WAW)
// Actions: (1) the instruction proceeds to the FU; (2) scoreboard updates its internal data structure
// If an instruction is stalled at this stage, no other instructions can proceed

public class IssueStage {
	public static int prev_inst_index = -1;
	public static int curr_inst_index = -1;
	public static int output_index = -1;

	public static void execute() throws Exception {
		if(FetchStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(FetchStage.prev_inst_index);
			
			if(canIssue(inst)){
				curr_inst_index = FetchStage.prev_inst_index;
				System.out.println("Issue- " + curr_inst_index + " - " + inst.toString());
				FetchUnit.i.setBusy(false);
				IssueUnit.i.setBusy(true);
				inst.markRegisterStatus();

				OutputManager.output_table.get(output_index)[2] = MIPS.cycle;
				ReadOperandsStage.output_index = output_index;

				prev_inst_index = curr_inst_index;
				curr_inst_index++;
				IssueUnit.i.execute(inst);					
				ExecutionUnit.allocate_unit(inst, curr_inst_index, output_index);
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
		
		if(!ExecutionUnit.isUnitAvailable(inst)){  // check structural hazards
			MIPS.print("Structural Hazard");
			OutputManager.output_table.get(output_index)[8] = 1;
			return false;
		}
		
		if(inst.isDestinationBeingWritten()){ // check WAW hazards
			MIPS.print("WAW Hazard");
			OutputManager.output_table.get(output_index)[7] = 1;
			return false;
		}
		
		return true;
	}
}
