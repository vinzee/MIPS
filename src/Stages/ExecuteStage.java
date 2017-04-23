package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.FunctionalUnitManager;

// Execution — operate on operands (EX)
// Actions: The functional unit begins execution upon receiving operands.
//          When the result is ready, it notifies the scoreboard that it has completed execution.

public class ExecuteStage {
	public static int prev_inst_index = -1;
	public static int curr_inst_index = -1;		

	public static void execute() throws Exception {
		if(ReadOperandsStage.prev_inst_index != -1){
			Instruction inst = MIPS.instructions.get(ReadOperandsStage.prev_inst_index);
			
			if(canExecute(inst)){
				System.out.println("Execute- " + ReadOperandsStage.prev_inst_index + " - " + inst.toString());
				curr_inst_index = ReadOperandsStage.prev_inst_index;
				ReadOperandUnit.i.setBusy(false);

				FunctionalUnit unit = FunctionalUnitManager.allocate_unit(inst, curr_inst_index);
	        	unit.setBusy(true);
				
				prev_inst_index = curr_inst_index;
				curr_inst_index++;
			}else{
				// initiate stall
				prev_inst_index = -1;
				System.out.println("No execution unit found");
			}
		}else{
			// relay stall ahead
			prev_inst_index = -1;
		}
		
		Instruction inst_finished_execution = FunctionalUnitManager.execute_busy_units();

		if(inst_finished_execution != null){
			inst_finished_execution.execute();
		}
	}

	private static boolean canExecute(Instruction inst) throws Exception {
		// already checked for availability of execution unit in issue
		return true;
	}
}
