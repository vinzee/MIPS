package Cache;

import java.util.Arrays;

import MIPS.MIPS;

// Direct-mapped cache
// access (hit) time of one cycle per word
// I-Cache and D-Cache are both connected to main memory using a shared bus
// http://www.cs.utexas.edu/users/mckinley/352/lectures/16.pdf

public class ICacheManager {
	public static int no_of_blocks;
	public static int block_size;
	public static int[][] i_cache;
	public static boolean busy;
	public static int latency;
	public static int remaining_latency = 0;
	public static final int LATENCY_FOR_ONE = 3;
	
	public static void init(){
		i_cache = new int[no_of_blocks][block_size]; 
		for(int[] row:i_cache)
			Arrays.fill(row, -1);
		latency = block_size * LATENCY_FOR_ONE;
		
//		debug();
	}
	
	public static boolean is_available(int block_address){
		if(!MIPS.CACHING_ENABLED) return true;
		
		is_valid_address(block_address);
			
		if(busy){ // fetching into cache
			MIPS.print("fetching into cache: " + block_address + " (" + remaining_latency + ")");
			remaining_latency--;
			if(remaining_latency == 0) busy = false;
			return false;
		}else{
			if(is_present(block_address)){ // cache hit
				MIPS.print("Cache hit: " + block_address);
				return true;
			}else{ // cache miss
				MIPS.print("Cache miss: " + block_address);
				insert(block_address);
				busy = true;
				remaining_latency = latency;
				remaining_latency--;
				return false;
			}
		}
	}
	
	private static void is_valid_address(int block_address) {
		if(block_address > 100){
			throw new Error("Invalid block address: " + block_address);			
		}
	}

	private static boolean is_present(int block_address){
		return block_address == get(block_address);
	}

	private static int get(int block_address){
		int cache_block_address = (block_address / block_size) % no_of_blocks;
		int cache_block_offset = block_address % no_of_blocks;

		return i_cache[cache_block_address][cache_block_offset];
	}

	private static void insert(int block_address){
		int cache_block_address = (block_address / block_size) % no_of_blocks;
		
		for(int i=0;i<4;i++){
			i_cache[cache_block_address][i] = block_address + i;
		}
	}

	private static void debug(){
		System.out.println("is_present(0): " + is_present(0));
		insert(0);
		System.out.println("is_present(0): " + is_present(0));
		System.out.println("is_present(1): " + is_present(1));
		System.out.println("is_present(2): " + is_present(2));
		System.out.println("is_present(3): " + is_present(3));

		System.out.println("get(16): " + get(16));
		System.out.println("is_present(16): " + is_present(16));

	}
}
