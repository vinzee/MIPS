package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;

public class ExecuteStage {
	public static int prev_inst_index = -1;
	public static int curr_inst_index = -1;		

	public static void execute() {
		if(ReadOperandsStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(ReadOperandsStage.prev_inst_index);
			
			if(canIssue(inst)){
				System.out.println("Execute- " + ReadOperandsStage.prev_inst_index + " - " + inst.toString());
				curr_inst_index = ReadOperandsStage.prev_inst_index;
				ReadOperandUnit.i.setBusy(false);
				ExecuteUnit.i.setBusy(true);
				ExecuteUnit.i.execute(inst);
				
				prev_inst_index = curr_inst_index;
				curr_inst_index++;
			}		
		}else{
			prev_inst_index = -1;
		}
	}

	private static boolean canIssue(Instruction inst) {
		// TODO Auto-generated method stub
		return !ExecuteUnit.i.isBusy();
	}
}
