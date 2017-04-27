package Stages;

import java.util.ArrayList;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

public class WriteStage {
	public static ArrayList<Integer> gid_queue = new ArrayList<Integer>();
	public static int prev_gid = -1;
	
	public static void execute() throws Exception {
		if(prev_gid != -1){
			FunctionalUnitData prev_fud = ExecutionUnit.busy_units.get(prev_gid);
			if(prev_fud != null){
				ExecutionUnit.deallocate_unit(prev_fud);
				Instruction prev_inst = MIPS.instructions.get(prev_fud.id);
				prev_inst.unMarkRegisterStatus();
			}
		}

		if(gid_queue.size() != 0){
			if(gid_queue.get(0) == -1){ gid_queue.remove(0); return; }

			int gid = gid_queue.remove(0);
			int id = OutputManager.read(gid, 0);
			Instruction inst = MIPS.instructions.get(id);
			
			System.out.println("Write: " + gid + " - " + inst.toString());
			WriteUnit.i.execute(inst);

			ExecutionUnit.stop_unit(gid);
			OutputManager.write(gid, 5, MIPS.cycle);
			prev_gid = gid;
		}else{
			prev_gid = -1;
		}
	}

//	private static boolean canWrite(Instruction inst) throws Exception {
		// TODO - Ask if this is to be checked
//		if(inst.isDestinationBeingRead()){ // check WAR hazards
//			System.out.println("WAR hazard(DestinationBeingRead): " + inst.toString());
//			OutputManager.write_silent(gid_queue.peek(), 9, 1);
//			return false;
//		}ssss
//		return true;
//	}
}
