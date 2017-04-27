package Stages;

import FunctionalUnits.FetchUnit;
import Instructions.Instruction;
import MIPS.MIPS;
import Managers.OutputManager;

// latency of 1;
public class FetchStage {	
	private static int id = 0;
	
	public static void execute() {
		if(canFetch() && id != -1){
			Instruction inst = MIPS.instructions.get(id);
			if(inst == null) throw new Error("Invalid instruction index: " + id);
				
			System.out.println("Fetch- " + id + " - " + inst.toString());
			
			int gid = OutputManager.add();
			FetchUnit.i.execute(inst, gid);

			OutputManager.write(gid, 0, id);
			OutputManager.write(gid,1, MIPS.cycle);
			
			id++;
		}		
	}
	
	private static boolean canFetch() {
		return !FetchUnit.i.isBusy();
	}

	public static void setId(int new_id) {
		id = new_id;
		FetchUnit.i.setBusy(false);
	}

}
