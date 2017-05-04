package Cache;

import java.util.HashSet;

import Instructions.Instruction;
import Instructions.LD;
import Instructions.LW;
import Instructions.SD;
import Instructions.SW;
import MIPS.MIPS;
import Managers.MemoryManager;

public class CacheManager {
	public static final boolean logging = true;
	public static final int LATENCY_PER_WORD = 3;
	public static int icache_misses = 0;
	public static int icache_hits = 0;
	public static int icache_requests = 0;
	public static int dcache_misses = 0;
	public static int dcache_hits = 0;
	public static int dcache_requests = 0;
	public static HashSet<String> icache_processed_addresses = new HashSet<String>();
	public static HashSet<String> dcache_processed_addresses = new HashSet<String>();

	public static void init() throws Exception {
		ICacheManager.init();
		DCacheManager.init();
	}

	public static boolean is_available_in_d_cache(int gid, Instruction inst) throws Exception {
		if(!MIPS.CACHING_ENABLED) return true;

		boolean is_load = (inst instanceof LD) || (inst instanceof LW);
		boolean is_store = (inst instanceof SD) || (inst instanceof SW);

		if(!is_load && !is_store) return true;

		int address = inst.getMemoryOperand().final_address();

		address = MemoryManager.translate_address(address);
		boolean out = _is_available_in_d_cache(address, gid, inst, is_store);

		if(out && (inst instanceof LD || inst instanceof SD)){
			int address2 = address + 1;
			MemoryManager.translate_address(address2);
			out = out && _is_available_in_d_cache(address2, gid, inst, is_store);
		}

		return out;

	}

	private static boolean _is_available_in_d_cache(int address, int gid, Instruction inst, boolean is_store) throws Exception{
		if(DCacheManager.busy){
			return false;
		}else{
			String key = Integer.toString(gid) + Integer.toString(address);
			if(!dcache_processed_addresses.contains(key)) dcache_requests += 1;

			if(DCacheManager.is_present(address)){ // cache hit
				if(!dcache_processed_addresses.contains(key)) print("hit: " + address + "  inst: " + inst.toString());
				DCacheManager.process_read(address);
				if(!dcache_processed_addresses.contains(key)) dcache_hits += 1;
				dcache_processed_addresses.add(key);
				return true;
			}else{ // cache miss
				if(!dcache_processed_addresses.contains(key)) print("miss: " + address + "  inst: " + inst.toString());
				if(!ICacheManager.busy){
					DCacheManager.process_write(address, is_store);
				}
				if(!dcache_processed_addresses.contains(key)) dcache_misses += 1;
				dcache_processed_addresses.add(key);
				return false;
			}
		}
	}

	public static boolean is_available_in_icache(int block_address){
		if(!MIPS.CACHING_ENABLED) return true;

		ICacheManager.is_valid_address(block_address);

		if(ICacheManager.busy){ // cache line not busy
			return false;
		}else{
			String key = Integer.toString(block_address);
			if(!icache_processed_addresses.contains(key)) icache_requests += 1;

			if(ICacheManager.is_present(block_address)){ // cache hit
//				print("ICache hit: " + block_address);
				if(!icache_processed_addresses.contains(key)) icache_hits += 1;
				icache_processed_addresses.add(key);
				return true;
			}else{ // cache miss
//				print("ICache miss: " + block_address);
				if(!DCacheManager.busy){
					ICacheManager.process_write(block_address);
				}
				if(!icache_processed_addresses.contains(key)) icache_misses += 1;
				icache_processed_addresses.add(key);
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

	public static void print(String msg){
		if(logging) MIPS.print(msg);
	}

}
