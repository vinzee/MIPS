package Cache;

import MIPS.MIPS;

//I-Cache and D-Cache are both connected to main memory using a shared bus

// 2-way set associative = 2 blocks in set
// 4-words blocks
// hit time - one cycle per word
//• Write-back strategy
//• Write-allocate policy
//• LRU replacement
public class DCacheManager {
	public static DCacheSet[] d_cache_sets;
	public static int no_of_sets = 4;
	public static boolean busy;
	public static int latency;

	public static DCacheRequest dcache_request;

	public static int index_length = (int) (Math.log(no_of_sets) / Math.log(2));
	public static int index_mask;

	public static void init() throws Exception{
		d_cache_sets = new DCacheSet[no_of_sets];

		for(int i=0;i<no_of_sets;i++) d_cache_sets[i] = new DCacheSet();

		latency = no_of_sets * CacheManager.LATENCY_PER_WORD;
		index_mask = CacheManager.get_mask(index_length, 0);

//		debug();
	}

	public static boolean is_present(int address) {
		int set_id = address & index_mask;
		DCacheSet set = d_cache_sets[set_id];
        int base_address = address >> index_length;
		System.out.println("set_id:" + set_id + " , base_address: " + base_address);
		print_state();
		return set.does_address_exist(base_address);
	}

    public static void write_block() throws Exception {
    	dcache_request.block.base_address = dcache_request.base_address;
    	dcache_request.block.dirty = dcache_request.store;
    	dcache_request.set.toggle_lru(dcache_request.block);
		busy = false;
		dcache_request = null;
    }

    public static int process_write(int address, boolean store) throws Exception {
		int set_id = address & index_mask;
		DCacheSet set = d_cache_sets[set_id];
        int base_address = address >> index_length;
        DCacheBlock block = null;
        int total_latency = 0;

//        System.out.println("$$set_id:" + set_id + " , base_address: " + base_address);

        if (set.has_free_block()){
            block = set.get_empty_block(base_address);
            total_latency += DCacheManager.latency;
        }else{
            block = set.get_lru_block();
            total_latency += DCacheManager.latency;
//            total_latency += DCacheManager.latency; // latency for eviction
            // TODO - if dirty, save block to memory before eviction
        }
        if (block == null) throw new Exception("DCache cannot find a null block");

        dcache_request = new DCacheRequest(address, base_address, set, block, store, total_latency, total_latency);

        busy = true;

        return total_latency;
    }

	public static void process_read(int address) {
		int set_id = address & index_mask;
		DCacheSet set = d_cache_sets[set_id];
        int base_address = address >> index_length;

        DCacheBlock block = set.get_address_block(base_address);

        set.toggle_lru(block);
	}

	public static void run() throws Exception{
		if(busy){
			dcache_request.remaining_latency--;
			if(dcache_request.remaining_latency == 0){
				write_block();
				MIPS.print("DCache: Fetched from cache !!!");
			}
		}
	}

	private static void print_state(){
		System.out.println("---------------");
		for(int i=0;i<d_cache_sets.length;i++)
			System.out.println(d_cache_sets[i]);
		System.out.println("---------------");
	}

	private static void debug() throws Exception {
		System.out.println(is_present(266));
		System.out.println(process_write(266, true));
		System.out.println(is_present(266));
//		print_state();
		write_block();
//		print_state();
		System.out.println(is_present(266));
	}

}
