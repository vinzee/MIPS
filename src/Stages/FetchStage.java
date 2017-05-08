package Stages;

import java.util.ArrayList;

import Cache.CacheManager;
import FunctionalUnits.FetchUnit;
import Instructions.Instruction;
import MIPS.MIPS;
import Managers.*;

// latency of 1;
public class FetchStage {
	private static int id = 0;
	public static ArrayList<Integer> id_queue = new ArrayList<Integer>();
	public static int unused_gid = -1;
	private static int prev_gid = -1;
	public static int halts_processed = 0;

	public static void execute() {
		if(canFetch() && id != -1){
			Instruction inst = MIPS.instructions.get(id);
			if(inst == null) throw new Error("Invalid instruction index: " + id);

			int gid = (unused_gid == -1) ? OutputManager.add() : unused_gid;

			if(CacheManager.is_available_in_icache(id, gid)){
//				System.out.println("Fetch- " + id + " - " + inst.toString());
				unused_gid = -1;
				FetchUnit.i.execute(inst, gid);

				OutputManager.add_row();
				OutputManager.write(gid, 0, id);
				OutputManager.write(gid,1, MIPS.cycle);

				if(MIPS.jump){
					MIPS.jump = false;
					id = id_queue.remove(0);
				}else if(MIPS.halt){
					id = -1;
				}else{
					id = id+1;
				}
				prev_gid = gid;
			}else{
				unused_gid = gid;
			}
		}else{
			if(id_queue.size() != 0) id = id_queue.remove(0);
			MIPS.jump = false;
			if(!MIPS.jump){
				if(id != -1) CacheManager.is_available_in_icache(id, prev_gid+1);
			}
		}
	}

	private static boolean canFetch() {
		return !FetchUnit.i.isBusy();
	}

	public static void setId(int new_id) {
		id = new_id;
		FetchUnit.i.setBusy(false);
	}
	public static void setNextId(int new_id) {
		id_queue.add(new_id);
		FetchUnit.i.setBusy(false);
	}

}
