package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;


public class WriteStage {
//	public static int prev_inst_index = -1;
	public static int curr_inst_index = -1;		
	public static int output_index = -1;
	public static int previous_output_index = -1;

	public static void execute() throws Exception {
		if(previous_output_index != -1){
			System.out.println("previous_output_index - " + previous_output_index);
			FunctionalUnitData prev_fud = ExecutionUnit.busy_units.get(previous_output_index);
			if(prev_fud != null){
				ExecutionUnit.deallocate_unit(prev_fud.unit);	        	
				ExecutionUnit.busy_units.remove(previous_output_index);						
			}
		}

		if(ExecuteStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(ExecuteStage.prev_inst_index);
			
			if(canWrite(inst)){
				curr_inst_index = ExecuteStage.prev_inst_index;
				System.out.println("Write- " + ExecuteStage.prev_inst_index + " - " + inst.toString());
//				ExecuteUnit.i.setBusy(false);
//				WriteUnit.i.setBusy(true);

				WriteUnit.i.execute(inst);
				OutputManager.output_table.get(output_index)[5] = MIPS.cycle;
				
				ExecutionUnit.stop_unit(output_index);
				
				previous_output_index = output_index;
				
				inst.unMarkRegisterStatus();
				
//				prev_inst_index = curr_inst_index;
				curr_inst_index++;
			}		
		}
//		else{
//			prev_inst_index = -1;
//		}
	}

	private static boolean canWrite(Instruction inst) throws Exception {
		if(WriteUnit.i.isBusy()) return false;
		
		if(inst.isDestinationBeingWritten()){ // check WAR hazards
			System.out.println("WAR hazard");
			OutputManager.output_table.get(output_index)[9] = 1;
			return false;
		}
		
		return true;
	}
}
