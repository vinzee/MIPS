package Stages;

import java.util.ArrayList;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

// Read operands — wait until no data hazards, then read operands
// Wait conditions: all source operands are available
// Actions: the function unit reads register operands and start execution the next cycle

public class ReadOperandsStage {
	public static ArrayList<Integer> gid_queue = new ArrayList<Integer>();
	
	public static void execute() throws Exception {
		if(gid_queue.size() != 0){
			if(gid_queue.get(0) == -1){ gid_queue.remove(0); return; }

			for (int i = 0;i < gid_queue.size();i++) {
				int gid = gid_queue.get(i);
				int id = OutputManager.read(gid, 0);
				Instruction inst = MIPS.instructions.get(id);

				if(canReadOperands(inst, gid)){
					gid = gid_queue.remove(i);
					System.out.println("Read- " + gid + " - " + inst.toString());
					inst.markDestinationRegisterStatus();

					ReadOperandUnit.i.execute(inst, gid);

					OutputManager.write(gid, 3, MIPS.cycle);
					break;
				}else{
					// Till BEQ / BNE is issued stall pipeline until the condition is resolved.
					if(inst instanceof BEQ || inst instanceof BNE){
						IssueStage.gid_queue.add(0, -1);
						break;
					}
				}
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
