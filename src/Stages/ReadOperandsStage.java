package Stages;

import java.util.LinkedList;
import java.util.Queue;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

// Read operands — wait until no data hazards, then read operands
// Wait conditions: all source operands are available
// Actions: the function unit reads register operands and start execution the next cycle

public class ReadOperandsStage {
	public static Queue<Integer> gid_queue = new LinkedList<Integer>();
	
	public static void execute() throws Exception {
		if(gid_queue.size() != 0){
			if(gid_queue.peek() == -1){ gid_queue.remove(); return; }

			int id = OutputManager.read(gid_queue.peek(), 0);
			Instruction inst = MIPS.instructions.get(id);
			
			if(canReadOperands(inst)){
				int gid = gid_queue.remove();
				System.out.println("Read- " + gid + " - " + inst.toString());
				IssueUnit.i.setBusy(false);
				ReadOperandUnit.i.setBusy(true);
				inst.markDestinationRegisterStatus();
				
				ReadOperandUnit.i.execute(inst);

				OutputManager.write(gid, 3, MIPS.cycle);
				ExecuteStage.gid_queue.add(gid);				
			}
		}
	}

	private static boolean canReadOperands(Instruction inst) throws Exception {
//		if(ReadOperandUnit.i.isBusy()){
//			MIPS.print("ReadOperandUnit.i.isBusy: " + inst.toString());
//			return false;
//		}
		
		if(inst.areSourcesBeingWritten()){ // check RAW hazards
			MIPS.print("RAW hazard(SourcesBeingWritten):" + inst.toString());
			OutputManager.write_silent(gid_queue.peek(), 6, 1);
			return false;
		}

		return true;
	}
}
