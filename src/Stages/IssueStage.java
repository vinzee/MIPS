package Stages;

import java.util.ArrayList;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

// Issue — decode instructions & check for structural hazards
// Wait conditions: (1) the required FU is free; (2) no other instruction writes to the same register destination (to avoid WAW)
// Actions: (1) the instruction proceeds to the FU; (2) scoreboard updates its internal data structure
// If an instruction is stalled at this stage, no other instructions can proceed

public class IssueStage {
	public static ArrayList<Integer> gid_queue = new ArrayList<Integer>();

	public static void execute() throws Exception {
		if(gid_queue.size() != 0){
			if(gid_queue.get(0) == -1){ gid_queue.remove(0); return; }

			int id = OutputManager.read(gid_queue.get(0), 0);
			Instruction inst = MIPS.instructions.get(id);
			
			if(canIssue(inst, gid_queue.get(0))){
				int gid = gid_queue.remove(0);
				System.out.println("Issue: " + gid + " - " + inst.toString());
				FetchUnit.i.setBusy(false);

				IssueUnit.i.execute(inst, gid, id);

				OutputManager.write(gid, 2, MIPS.cycle);
			}else{
				// Till BEQ / BNE is issued stall pipeline until the condition is resolved.
				if(inst instanceof BEQ || inst instanceof BNE) gid_queue.add(0, -1);
			}
		}
	}

	private static boolean canIssue(Instruction inst, int gid) throws Exception {
		if(!ExecutionUnit.isUnitAvailable(inst)){  // check structural hazards
//			MIPS.print("Structural Hazard: " + inst.toString());
			OutputManager.write_silent(gid, 8, 1);
			return false;
		}
		
		if(inst.isDestinationBeingWritten()){ // check WAW hazards
//			MIPS.print("WAW Hazard(DestinationBeingWritten): " + inst.toString());
			OutputManager.write_silent(gid, 7, 1);
			return false;
		}
		
		return true;
	}
}
