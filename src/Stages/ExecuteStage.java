package Stages;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

// Execution — operate on operands (EX)
// Actions: The functional unit begins execution upon receiving operands.
//          When the result is ready, it notifies the scoreboard that it has completed execution.

public class ExecuteStage {
	public static void execute() throws Exception {
		if(ReadOperandsStage.prev_gid != -1){
			int id = OutputManager.read(ReadOperandsStage.prev_gid, 0);
			Instruction inst = MIPS.instructions.get(id);
			
			if(canExecute(inst)){
				int gid = ReadOperandsStage.prev_gid;
				System.out.println("Execute- " + gid + " - " + inst.toString());
				ReadOperandUnit.i.setBusy(false);

				inst.markSourceRegisterStatus();
				ExecutionUnit.run_unit(gid);	    		
			}
		}
		
		FunctionalUnitData fud = ExecutionUnit.execute_busy_units();
		if(fud != null){
			Instruction inst_finished_execution = MIPS.instructions.get(fud.id);
			inst_finished_execution.execute();
        	OutputManager.write(fud.gid, 4, MIPS.cycle);
        	WriteStage.gid_queue.add(fud.gid);     	
		}
	}

	private static boolean canExecute(Instruction inst) throws Exception {
		// already checked for availability of execution unit in issue
		return true;
	}
}
