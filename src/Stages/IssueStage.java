package Stages;

import java.util.LinkedList;
import java.util.Queue;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

// Issue — decode instructions & check for structural hazards
// Wait conditions: (1) the required FU is free; (2) no other instruction writes to the same register destination (to avoid WAW)
// Actions: (1) the instruction proceeds to the FU; (2) scoreboard updates its internal data structure
// If an instruction is stalled at this stage, no other instructions can proceed

public class IssueStage {
	public static Queue<Integer> gid_queue = new LinkedList<Integer>();

	public static void execute() throws Exception {
		if(gid_queue.size() != 0){
			if(gid_queue.peek() == -1){ gid_queue.remove(); return; }

			int id = OutputManager.read(gid_queue.peek(), 0);
			Instruction inst = MIPS.instructions.get(id);
			
			if(canIssue(inst)){
				int gid = gid_queue.remove();
				System.out.println("Issue: " + gid + " - " + inst.toString());
				FetchUnit.i.setBusy(false);

				IssueUnit.i.execute(inst, gid);					
				ExecutionUnit.allocate_unit(inst, id, gid);

				OutputManager.write(gid, 2, MIPS.cycle);				
				IssueUnit.i.setBusy(false);
			}		
		}
	}

	private static boolean canIssue(Instruction inst) throws Exception {
//		if(IssueUnit.i.isBusy()){
//			MIPS.print("IssueUnit.i.isBusy: " + inst.toString());
//			return false;
//		}
		
		if(!ExecutionUnit.isUnitAvailable(inst)){  // check structural hazards
			MIPS.print("Structural Hazard: " + inst.toString());
			OutputManager.write_silent(gid_queue.peek(), 8, 1);
			return false;
		}
		
		if(inst.isDestinationBeingWritten()){ // check WAW hazards
			MIPS.print("WAW Hazard(DestinationBeingWritten): " + inst.toString());
			OutputManager.write_silent(gid_queue.peek(), 7, 1);
			return false;
		}
		
		return true;
	}
}
