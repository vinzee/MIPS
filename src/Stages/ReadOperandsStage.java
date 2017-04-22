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

	public static void execute() {
		if(IssueStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(IssueStage.prev_inst_index);
			
			if(canIssue(inst)){
				System.out.println("Read- " + IssueStage.prev_inst_index + " - " + inst.toString());
				curr_inst_index = IssueStage.prev_inst_index;
				IssueUnit.i.setBusy(false);
				ReadOperandUnit.i.setBusy(true);
				ReadOperandUnit.i.execute(inst);
				
				prev_inst_index = curr_inst_index;
				curr_inst_index++;
			}		
		}else{
			prev_inst_index = -1;
		}
	}

	private static boolean canIssue(Instruction inst) {
		// TODO Auto-generated method stub
		return !ReadOperandUnit.i.isBusy();
	}
}
