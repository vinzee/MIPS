package Stages;

import FunctionalUnits.FetchUnit;
import Instructions.Instruction;
import MIPS.MIPS;
import Managers.OutputManager;

// latency of 1;
public class FetchStage {	
	public static int id = 0;
	
	public static void execute() {
		if(canFetch() && id != -1){
			Instruction inst = MIPS.instructions.get(id);
			if(inst == null) throw new Error("Invalid instruction index: " + id);
				
			System.out.println("Fetch- " + id + " - " + inst.toString());
			FetchUnit.i.setBusy(true);
			FetchUnit.i.execute(inst);
			
			int gid = OutputManager.add();
			OutputManager.write(gid, 0, id);
			OutputManager.write(gid,1, MIPS.cycle);
			
			IssueStage.gid_queue.add(gid);
			id++;
		}		
	}
	
	private static boolean canFetch() {
		return !FetchUnit.i.isBusy();
	}

}
