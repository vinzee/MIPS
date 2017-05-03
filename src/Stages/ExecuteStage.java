package Stages;

import java.util.ArrayList;

import Cache.CacheManager;
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

			for (int i = 0;i < gid_queue.size();i++) {
				int gid = gid_queue.get(i);
				int id = OutputManager.read(gid, 0);
				Instruction inst = MIPS.instructions.get(id);

				if(CacheManager.is_available_in_d_cache(gid, inst)){
					gid = gid_queue.remove(i);
					System.out.println("Execute- " + gid + " - " + inst.toString());
					ExecutionUnit.run_unit(gid);
		        	OutputManager.write(gid, 9, MIPS.cycle);
					break;
				}
			}
		}

		ArrayList<FunctionalUnitData> out = ExecutionUnit.execute_busy_units();

		for(FunctionalUnitData fud: out){
			Instruction inst_finished_execution = MIPS.instructions.get(fud.id);
			inst_finished_execution.execute();
			inst_finished_execution.write();
        	OutputManager.write(fud.gid, 4, MIPS.cycle);
        	WriteStage.gid_queue.add(fud.gid);
		}
	}
}
