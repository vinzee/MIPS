package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;

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
		// TODO Auto-generated method stub
		return !IssueUnit.i.isBusy();
	}
}
