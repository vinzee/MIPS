package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;

// Read operands — wait until no data hazards, then read operands
// Wait conditions: all source operands are available
// Actions: the function unit reads register operands and start execution the next cycle

public class ReadOperandsStage {
	public static int prev_inst_index = -1;
	public static int curr_inst_index = -1;		

	public static void execute() throws Exception {
		if(IssueStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(IssueStage.prev_inst_index);
			
			if(canIssue(inst)){
				System.out.println("Read- " + IssueStage.prev_inst_index + " - " + inst.toString());
				curr_inst_index = IssueStage.prev_inst_index;
				IssueUnit.i.setBusy(false);
				ReadOperandUnit.i.setBusy(true);
				
				prev_inst_index = curr_inst_index;
				curr_inst_index++;

				ReadOperandUnit.i.execute(inst);
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
		if(ReadOperandUnit.i.isBusy()) return false;
		
		if(inst.areSourcesBeingWritten()){ // check RAW hazards
			System.out.println("RAW hazard");
			return false;
		}

		return true;
	}
}
