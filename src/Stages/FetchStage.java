package Stages;

import FunctionalUnits.FetchUnit;
import Instructions.Instruction;
import MIPS.MIPS;

// latency of 1;
public class FetchStage {
	
	public static int prev_inst_index = -1;
	public static int curr_inst_index = 0;
	
	public static void execute() {
		if(curr_inst_index != -1){
			Instruction inst = MIPS.instructions.get(curr_inst_index);
			if(inst == null){
				throw new Error("Invalid instruction index");
			}
					
			if(canFetch(inst)){
				System.out.println("Fetch- " + curr_inst_index + " - " + inst.toString());
				FetchUnit.i.setBusy(true);
				FetchUnit.i.execute(inst);
				prev_inst_index = curr_inst_index;
				curr_inst_index++;
			}
		}else{
			prev_inst_index = -1;
		}
	}
	
	private static boolean canFetch(Instruction inst) {
		// TODO - check whether previous inst has moved forward
		return !FetchUnit.i.isBusy();
	}

}
