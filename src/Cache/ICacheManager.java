package Cache;

import java.util.Arrays;

import MIPS.MIPS;

// Direct-mapped cache
// access (hit) time of one cycle per word

public class ICacheManager {
	public static int no_of_blocks;
	public static int block_size;
	public static int[][] i_cache;
	public static int tag_length;
	public static int index_length;
	public static int offset_length;
	public static int index_mask;
	public static int offset_mask;

	public static boolean busy;
	public static int latency;
	public static int remaining_latency = 0;
	public static int processing_block_address;
	public static final int LATENCY_FOR_ONE = 3;

	public static void init(){
		i_cache = new int[no_of_blocks][block_size];

		index_length = (int) (Math.log(no_of_blocks) / Math.log(2));
		offset_length = (int) (Math.log(block_size) / Math.log(2));

		index_mask = CacheManager.get_mask(index_length, offset_length);
		offset_mask = CacheManager.get_mask(offset_length, 0);

		for(int[] row:i_cache)
			Arrays.fill(row, -1);
		latency = block_size * LATENCY_FOR_ONE;
	}

	static void is_valid_address(int block_address) {
		if(block_address > 100){
			throw new Error("Invalid block address: " + block_address);
		}
	}

	static boolean is_present(int block_address){
		int tag = block_address >> (index_length + offset_length);
		return tag == get(block_address);
	}

	private static int get(int block_address){
		int index = (block_address & index_mask) >> offset_length;
		int offset = block_address & offset_mask;

		return i_cache[index][offset];
	}

	static void insert(int block_address){
		int tag = block_address >> (index_length + offset_length);
		int index = (block_address & index_mask) >> offset_length;
		int offset = block_address & offset_mask;

		block_address = block_address - offset;

		for(int i=0;i<4;i++){
			i_cache[index][i] = tag;
		}
	}

	public static void run(){
		if(busy){
			remaining_latency--;
			if(remaining_latency == 0){
				insert(processing_block_address);
				processing_block_address = 0;
				busy = false;
				MIPS.print("ICache: Fetched from cache !!!");
			}
		}
	}

	public static void debug(){
		System.out.println("is_present(0): " + is_present(0));
		insert(0);
		System.out.println("is_present(0): " + is_present(0));
		System.out.println("is_present(1): " + is_present(1));
		System.out.println("is_present(2): " + is_present(2));
		System.out.println("is_present(3): " + is_present(3));
		System.out.println("is_present(3): " + is_present(4));
		insert(16);
		System.out.println("is_present(0): " + is_present(16));
		System.out.println("is_present(0): " + is_present(0));
		System.out.println("is_present(1): " + is_present(1));
		System.out.println("is_present(2): " + is_present(2));
		System.out.println("is_present(3): " + is_present(3));
	}
}
