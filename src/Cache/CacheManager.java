package Cache;

import java.util.Arrays;
import java.util.HashMap;
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
	public static HashSet<Integer> icache_processed_addresses = new HashSet<Integer>();
//	public static int dcache_misses = 0;
//	public static int dcache_hits = 0;
//	public static int dcache_requests = 0;

	public static HashSet<Integer> dcache_miss_requests = new HashSet<Integer>();
	public static HashSet<Integer> dcache_processed_addresses = new HashSet<Integer>();
	public static HashSet<Integer> dcache_read_requests = new HashSet<Integer>();

	public static void init() throws Exception {
		ICacheManager.init();
		DCacheManager.init();
	}

	public static boolean is_available_in_d_cache(int gid, Instruction inst) throws Exception {
		if(!MIPS.CACHING_ENABLED) return true;

		boolean is_load = (inst instanceof LD) || (inst instanceof LW);
		boolean is_store = (inst instanceof SD) || (inst instanceof SW);
		boolean is_double = (inst instanceof SD) || (inst instanceof LD);

		if(!is_load && !is_store) return true;

		int address = inst.getMemoryOperand().final_address();

		address = MemoryManager.translate_address(address);
		boolean out = _is_available_in_d_cache(address, gid, inst, is_store, is_double, true);

		if(out && is_double){
			int address2 = address + 1;
			MemoryManager.translate_address(address2);
			out = out && _is_available_in_d_cache(address2, gid, inst, is_store, is_double, false);
		}

		if(out){
			dcache_read_requests.clear();
		}

		return out;

	}

	static boolean _is_available_in_d_cache(int address, int gid, Instruction inst, boolean is_store, boolean is_double, boolean first) throws Exception{
			int key = Integer.parseInt(Integer.toString(gid) + Integer.toString(address));
//			if(!dcache_processed_addresses.contains(key)) dcache_requests += 1;
			dcache_processed_addresses.add(key);

			if(DCacheManager.is_present(address)){ // cache hit
//				if(!dcache_processed_addresses.contains(key)) print("hit: " + address + "  inst: " + inst.toString() + " , cycle: " + MIPS.cycle);
				if(!is_double || (first && dcache_read_requests.contains(address)) || (!first && dcache_read_requests.contains(address-1))){
//					if(dcache_processed_addresses.contains(key)) dcache_hits += 1;
					dcache_read_requests.add(address);
					return true;
				}else{
					dcache_read_requests.add(address);
//					dcache_processed_addresses.add(key);
					DCacheManager.process_read(address);
					return false;
				}
			}else if(DCacheManager.busy){ // cache miss
//				dcache_processed_addresses.add(key);
				return false;
			}else{
				if(!dcache_processed_addresses.contains(key)) print("miss: " + address + "  inst: " + inst.toString() + " , store: " + is_store + " , cycle: " + MIPS.cycle);
				if(!ICacheManager.busy){
					DCacheManager.process_write(gid, address, is_store);
				}
//				if(!dcache_processed_addresses.contains(key)) dcache_misses += 1;
//				dcache_processed_addresses.add(key);
				dcache_miss_requests.add(key);
				return false;
			}
	}

	public static boolean is_available_in_icache(int block_address, int gid){
		if(!MIPS.CACHING_ENABLED) return true;

		ICacheManager.is_valid_address(block_address);

		if(ICacheManager.busy){ // cache line not busy
			return false;
		}else{
			int key = gid;
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
