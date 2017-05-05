package Stages;

import java.util.ArrayList;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

public class WriteStage {
	public static ArrayList<Integer> gid_queue = new ArrayList<Integer>();
	public static ArrayList<Integer> prev_gid_queue = new ArrayList<Integer>();

	public static void execute() throws Exception {
		if(prev_gid_queue.size() != 0){
			for (int i = 0;i < prev_gid_queue.size();i++) {
				int prev_gid = prev_gid_queue.remove(i);
				i--;
				FunctionalUnitData prev_fud = ExecutionUnit.busy_units.get(prev_gid);
				if(prev_fud != null){
					ExecutionUnit.deallocate_unit(prev_fud);
					Instruction prev_inst = MIPS.instructions.get(prev_fud.id);
					prev_inst.unMarkRegisterStatus();
				}else{
					throw new Error("FUD not found !");
				}
			}
		}

		if(gid_queue.size() != 0){
			if(gid_queue.get(0) == -1){ gid_queue.remove(0); return; }

			for (int i = 0;i < gid_queue.size();i++) {
				int gid = gid_queue.remove(i);
				i--;
				int id = OutputManager.read(gid, 0);
				Instruction inst = MIPS.instructions.get(id);

//				System.out.println("Write: " + gid + " - " + inst.toString());
				WriteUnit.i.execute(inst);

				ExecutionUnit.stop_unit(gid);
				OutputManager.write(gid, 5, MIPS.cycle);
				prev_gid_queue.add(gid);
			}
		}
	}
}
