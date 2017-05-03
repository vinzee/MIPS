package Cache;

import Instructions.Instruction;
import Instructions.LD;
import Instructions.LW;
import Instructions.SD;
import Instructions.SW;
import MIPS.MIPS;

public class CacheManager {

	public static boolean is_available_in_d_cache(int gid, Instruction inst) throws Exception {
		if(!MIPS.CACHING_ENABLED) return true;

		boolean is_load = (inst instanceof LD) || (inst instanceof LW);
		boolean is_store = (inst instanceof SD) || (inst instanceof SW);

		if(!is_load && !is_store) return true;

		int address = inst.getMemoryOperand().final_address();

		if(DCacheManager.is_present(address)){ // cache hit
			return true;
		}else{ // cache miss
			if(!isBusy()){
				DCacheManager.processing_block_address = address;
				DCacheManager.is_store = is_store;
				DCacheManager.remaining_latency = DCacheManager.write_block(address, is_store);
	//			DCacheManager.remaining_latency--;
				DCacheManager.busy = true;
			}
			return false;
		}
	}

	public static boolean is_available_in_icache(int block_address){
		if(!MIPS.CACHING_ENABLED) return true;

		ICacheManager.is_valid_address(block_address);

		if(ICacheManager.is_present(block_address)){ // cache hit
//			MIPS.print("ICache hit: " + block_address);
			return true;
		}else{ // cache miss
			if(!isBusy()){ // cache line not busy
				MIPS.print("ICache miss: " + block_address);
				ICacheManager.processing_block_address = block_address;
				ICacheManager.busy = true;
				ICacheManager.remaining_latency = ICacheManager.latency;
//	    			ICacheManager.remaining_latency--;
			}
			return false;
		}
	}

	public static void run() throws Exception{
		if(ICacheManager.busy){
			ICacheManager.run();
		}else if(DCacheManager.busy){
			DCacheManager.run();
		}
	}

	public static boolean isBusy(){
		return ICacheManager.busy || DCacheManager.busy;
	}

	static int get_mask(int ones, int zeros) {
		int mask = 0;

		for (int i = 0; i < ones; i ++)
			mask = (mask << 1) + 1;

		mask = mask << zeros;

		return mask;
	}

}
