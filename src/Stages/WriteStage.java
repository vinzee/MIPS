package Stages;

import java.util.LinkedList;
import java.util.Queue;

import FunctionalUnits.*;
import Instructions.*;
import MIPS.*;
import Managers.OutputManager;

public class WriteStage {
	public static Queue<Integer> gid_queue = new LinkedList<Integer>();
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
			if(gid_queue.peek() == -1){ gid_queue.remove(); prev_gid = -1; return; }

			int id = OutputManager.read(gid_queue.peek(), 0);
			Instruction inst = MIPS.instructions.get(id);
			
			if(canWrite(inst)){
				int gid = gid_queue.remove();
				System.out.println("Write: " + gid + " - " + inst.toString());
				WriteUnit.i.execute(inst);
				
				ExecutionUnit.stop_unit(gid);
				OutputManager.write(gid, 5, MIPS.cycle);
				prev_gid = gid;
			}else{
				prev_gid = -1;
			}
		}else{
			prev_gid = -1;			
		}
	}

	private static boolean canWrite(Instruction inst) throws Exception {
//		if(WriteUnit.i.isBusy()) return false;
		
		// TODO - Ask if this is to be checked
//		if(inst.isDestinationBeingRead()){ // check WAR hazards
//			System.out.println("WAR hazard(DestinationBeingRead): " + inst.toString());
//			OutputManager.write_silent(gid_queue.peek(), 9, 1);
//			return false;
//		}
		
		return true;
	}
}
