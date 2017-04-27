package Stages;

import java.util.ArrayList;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

// Execution — operate on operands (EX)
// Actions: The functional unit begins execution upon receiving operands.
//          When the result is ready, it notifies the scoreboard that it has completed execution.

public class ExecuteStage {
	public static ArrayList<Integer> gid_queue = new ArrayList<Integer>();

	public static void execute() throws Exception {
		if(gid_queue.size() != 0){
			if(gid_queue.get(0) == -1){ gid_queue.remove(0); return; }

			int gid = gid_queue.remove(0);
			int id = OutputManager.read(gid, 0);
			Instruction inst = MIPS.instructions.get(id);
			
			System.out.println("Execute- " + gid + " - " + inst.toString());

			ExecutionUnit.run_unit(gid);
		}
		
		FunctionalUnitData fud = ExecutionUnit.execute_busy_units();
		if(fud != null){
			Instruction inst_finished_execution = MIPS.instructions.get(fud.id);
			inst_finished_execution.execute();
        	OutputManager.write(fud.gid, 4, MIPS.cycle);
        	WriteStage.gid_queue.add(fud.gid);     	
		}
	}
}
