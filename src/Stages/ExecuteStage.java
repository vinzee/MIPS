package Stages;

import java.util.LinkedList;
import java.util.Queue;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

// Execution — operate on operands (EX)
// Actions: The functional unit begins execution upon receiving operands.
//          When the result is ready, it notifies the scoreboard that it has completed execution.

public class ExecuteStage {
	public static Queue<Integer> gid_queue = new LinkedList<Integer>();

	public static void execute() throws Exception {
		if(gid_queue.size() != 0){
//			if(gid_queue.peek() == -1){ gid_queue.remove(); return; }
			
			int id = OutputManager.read(gid_queue.peek(), 0);
			Instruction inst = MIPS.instructions.get(id);
			
			int gid = gid_queue.remove();
			System.out.println("Execute- " + gid + " - " + inst.toString());

//			inst.markSourceRegisterStatus();
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
