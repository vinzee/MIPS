package Stages;

import FunctionalUnits.FetchUnit;
import Instructions.Instruction;
import MIPS.MIPS;
import Managers.OutputManager;

// latency of 1;
public class FetchStage {
	
	public static int prev_inst_index = -1;
	public static int curr_inst_index = 0;
	public static int output_index = -1;
	
	public static void execute() {
		if(canFetch() && curr_inst_index != -1){
			Instruction inst = MIPS.instructions.get(curr_inst_index);
			if(inst == null) throw new Error("Invalid instruction index: " + curr_inst_index);
				
			System.out.println("Fetch- " + curr_inst_index + " - " + inst.toString());
			FetchUnit.i.setBusy(true);
			FetchUnit.i.execute(inst);

			OutputManager.add();
			output_index = OutputManager.last_output_index;
			OutputManager.output_table.get(output_index)[0] = curr_inst_index;
			OutputManager.output_table.get(output_index)[1] = MIPS.cycle;
			IssueStage.output_index = output_index;
			
			prev_inst_index = curr_inst_index;
			curr_inst_index++;
		}
		
		if(curr_inst_index == -1) prev_inst_index = -1;
	}
	
	private static boolean canFetch() {
		return !FetchUnit.i.isBusy();
	}

}
