package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;


public class WriteStage {
	public static int prev_inst_index = -1;
	public static int curr_inst_index = -1;		

	public static void execute() {
		if(ExecuteStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(ExecuteStage.prev_inst_index);
			
			if(canIssue(inst)){
				curr_inst_index = ExecuteStage.prev_inst_index;
				System.out.println("Write- " + ExecuteStage.prev_inst_index + " - " + inst.toString());
				ExecuteUnit.i.setBusy(false);
//				WriteUnit.i.setBusy(true);
				WriteUnit.i.execute(inst);
				
				prev_inst_index = curr_inst_index;
				curr_inst_index++;
			}		
		}else{
			prev_inst_index = -1;
		}
	}

	private static boolean canIssue(Instruction inst) {
		// TODO Auto-generated method stub
		return !WriteUnit.i.isBusy();
	}
}
