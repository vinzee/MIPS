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
//			if(gid_queue.peek() == -1){ gid_queue.remove(); return; }

				int gid = gid_queue.peek();
				int id = OutputManager.read(gid, 0);
				Instruction inst = MIPS.instructions.get(id);
				
				if(canReadOperands(inst, gid)){
					gid = gid_queue.remove();
					System.out.println("Read- " + gid + " - " + inst.toString());
					inst.markDestinationRegisterStatus();

					ReadOperandUnit.i.execute(inst, gid);

					OutputManager.write(gid, 3, MIPS.cycle);
				}
		}
	}

	private static boolean canReadOperands(Instruction inst, int gid) throws Exception {
		if(inst.areSourcesBeingWritten()){ // check RAW hazards
			MIPS.print("RAW hazard(SourcesBeingWritten):" + inst.toString());
			OutputManager.write_silent(gid, 6, 1);
			return false;
		}

		return true;
	}
}
