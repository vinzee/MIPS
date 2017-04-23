package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;


public class WriteStage {
	public static int prev_inst_index = -1;
	public static int curr_inst_index = -1;		

	public static void execute() throws Exception {
		if(ExecuteStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(ExecuteStage.prev_inst_index);
			
			if(canWrite(inst)){
				curr_inst_index = ExecuteStage.prev_inst_index;
				System.out.println("Write- " + ExecuteStage.prev_inst_index + " - " + inst.toString());
//				ExecuteUnit.i.setBusy(false);
//				WriteUnit.i.setBusy(true);
				WriteUnit.i.execute(inst);

				inst.unMarkRegisterStatus();
				
				prev_inst_index = curr_inst_index;
				curr_inst_index++;
			}		
		}else{
			prev_inst_index = -1;
		}
	}

	private static boolean canWrite(Instruction inst) throws Exception {
		if(WriteUnit.i.isBusy()) return false;
		
		if(inst.isDestinationBeingWritten()){ // check WAR hazards
			System.out.println("WAR hazard");
			return false;
		}
		
		return true;
	}
}
