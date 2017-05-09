package Cache;

import MIPS.MIPS;

//I-Cache and D-Cache are both connected to main memory using a shared bus

// 2-way set associative = 2 blocks in set
// 4-words blocks
// hit time - one cycle per word
//• Write-back strategy - write your change only to the cache (and it will be reflected in
//                        main memory when the block is replaced in the cache)
//• Write-allocate policy - when there is a write miss, the block is loaded in the cache
//                          and the write is reattempted
//• LRU replacement
public class DCacheManager {
	private static int no_of_sets = 2;
	static int block_size = 4;
	static int no_of_blocks = 2; // no_of_blocks within a set

	private static DCacheSet[] d_cache_sets = new DCacheSet[no_of_sets];
	private static DCacheRequest dcache_request;
	public static boolean busy;

	private static int set_id_length = (int) (Math.log(no_of_sets) / Math.log(2));
	private static int offset_length = (int) (Math.log(block_size) / Math.log(2));
	private static int set_id_mask;
	private static int latency;

	public static void init() throws Exception{
		for(int i=0;i<no_of_sets;i++) d_cache_sets[i] = new DCacheSet();
		latency = block_size * CacheManager.LATENCY_PER_WORD;
		set_id_mask = CacheManager.get_mask(set_id_length, offset_length);

//		debug();
	}

	public static boolean is_present(int address) {
		int set_id = get_set_id(address);
		DCacheSet set = d_cache_sets[set_id];
		int base_address = get_base_address(address);
		return set.does_address_exist(base_address);
	}

	public static void write_block() throws Exception {
		CacheManager.print("write: " + dcache_request.address + " , base_address: " + dcache_request.base_address + " , store:" + dcache_request.store + " , cycle:" + MIPS.cycle);
    	if(dcache_request.store){
        	dcache_request.block.dirty = true;
    	}
    	dcache_request.block.base_address = dcache_request.base_address;
    	dcache_request.block.no_of_reads = 0;
		busy = false;
		dcache_request = null;

		print_state();
    }

    public static int process_write(int gid, int address, boolean store) throws Exception {
		int set_id = get_set_id(address);
		DCacheSet set = d_cache_sets[set_id];
		int base_address = get_base_address(address);
        DCacheBlock block = null;
        int total_latency = 0;

        block = set.get_empty_block(base_address);
        if (block != null){
        	total_latency += DCacheManager.latency;
        }else{
            block = set.get_lru_block();
            if(block.dirty){ // write to memory only if there is an eviction, otherwise write to cache (no latency)
        		total_latency += DCacheManager.latency; // latency for eviction
            }
//
            total_latency += DCacheManager.latency;
        }
		CacheManager.print("start write: " + address + " , base_address: " + base_address + " , dirty:" + block.dirty + " , store:" + store + " , cycle:" + MIPS.cycle);
        dcache_request = new DCacheRequest(gid, address, base_address, set, block, store, total_latency);
        busy = true;

        return total_latency;
    }

	public static void process_read(int address) {
		int set_id = get_set_id(address);
		DCacheSet set = d_cache_sets[set_id];
		int base_address = get_base_address(address);
        DCacheBlock block = set.get_address_block(base_address);
        block.no_of_reads += 1;
	}

	public static void run() throws Exception{
		if(busy){
			dcache_request.remaining_latency--;
//			CacheManager.print("run: " + dcache_request.remaining_latency + " inst: " + dcache_request.address + " , cycle: " + MIPS.cycle);
			if(dcache_request.remaining_latency <= 0){
				write_block();
			}
		}

	}

	static void print_state(){
		if(CacheManager.logging){
			System.out.println("---------------");
			for(int i=0;i<d_cache_sets.length;i++)
				System.out.println(d_cache_sets[i]);
			System.out.println("---------------");
		}
	}

	private static int get_set_id(int address){
		return (address & set_id_mask) >> offset_length;
	}

    private static int get_base_address(int address) {
        return address >> (set_id_length+offset_length);
	}

	private static void debug() throws Exception {
//		System.out.println(is_present(266));
//		System.out.println(process_write(266, true));
//		System.out.println(is_present(266));
//		print_state();
//		write_block();
//		print_state();
//		System.out.println(is_present(266));
	}

}
