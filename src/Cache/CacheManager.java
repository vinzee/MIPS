package Cache;

import Instructions.Instruction;
import Instructions.LD;
import Instructions.LW;
import Instructions.SD;
import Instructions.SW;
import MIPS.MIPS;

public class CacheManager {
	public static final int LATENCY_PER_WORD = 3;
	public static int icache_hits = 0;
	public static int icache_requests = 0;
	public static int dcache_hits = 0;
	public static int dcache_requests = 0;

	public static void init() throws Exception {
		ICacheManager.init();
		DCacheManager.init();
	}

	public static boolean is_available_in_d_cache(int gid, Instruction inst) throws Exception {
		if(!MIPS.CACHING_ENABLED) return true;

		boolean is_load = (inst instanceof LD) || (inst instanceof LW);
		boolean is_store = (inst instanceof SD) || (inst instanceof SW);

		if(!is_load && !is_store) return true;

		int address;
		int address2;

		address = inst.getMemoryOperand().final_address();

//		if(inst instanceof LD || inst instanceof SD){
//			address2 = address + 1;
//		}else{
			address2 = address;
//		}

		boolean address_present = DCacheManager.is_present(address);
		boolean address2_present = DCacheManager.is_present(address2);
		icache_requests += (address == address2) ? 1 : 2;

		if(DCacheManager.busy){
			return false;
		}else{
			if(address_present && address2_present){ // cache hit
				DCacheManager.process_read(address);
				icache_hits += (address == address2) ? 1 : 2;
				return true;
			}else{ // cache miss
				if(!ICacheManager.busy){
					if(!address_present){
						DCacheManager.process_write(address, is_store);
					}else if(!address2_present){
						DCacheManager.process_write(address2, is_store);
					}else{
						throw new Error("all addresses present in cache");
					}
				}
				return false;
			}
		}
	}

	public static boolean is_available_in_icache(int block_address){
		if(!MIPS.CACHING_ENABLED) return true;

		ICacheManager.is_valid_address(block_address);

		dcache_requests++;

		if(ICacheManager.busy){ // cache line not busy
			return false;
		}else{
			if(ICacheManager.is_present(block_address)){ // cache hit
	//			MIPS.print("ICache hit: " + block_address);
				dcache_hits++;
				return true;
			}else{ // cache miss
	//			MIPS.print("ICache miss: " + block_address);
				if(!DCacheManager.busy){
					ICacheManager.process_write(block_address);
				}
				return false;
			}
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
