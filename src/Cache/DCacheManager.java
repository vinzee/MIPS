package Cache;

import MIPS.MIPS;

// 2-way set associative = 2 blocks in set
// 4-words blocks
// hit time - one cycle per word
//• Write-back strategy
//• Write-allocate policy
//• LRU replacement
public class DCacheManager {
	public static int no_of_blocks = 2; // no_of_blocks in set
	public static int no_of_sets = 4;
	public static DCacheSet[] d_cache_sets;
	public static boolean busy;
	public static final int LATENCY_PER_WORD = 3;
	public static int latency;
	public static int remaining_latency = 0;
	public static int prev_gid = -1;
	public static boolean is_store;
	public static int processing_block_address;

	public static void init(){
		d_cache_sets = new DCacheSet[no_of_sets];

		for(int i=0;i<no_of_sets;i++){
			d_cache_sets[i] = new DCacheSet();
		}

		latency = no_of_sets * LATENCY_PER_WORD;
	}

	static DCacheSet getSet(int address){
		int set_id = address & 0b10000;
		set_id = set_id >> 4;
		return d_cache_sets[set_id];
	}

	static int getBaseAddress(int address){
        int base_address = address >> 2;
        base_address = base_address << 2;
        return base_address;
    }

	public static boolean is_present(int address) {
		DCacheSet set = DCacheManager.getSet(address);
        int base_address = DCacheManager.getBaseAddress(address);

        return set.doesAddressExist(base_address);
	}

	//	set.isLRUBlockDirty()

    public static int write_block(int address, boolean store) throws Exception {
		DCacheSet set = DCacheManager.getSet(address);
        int base_address = DCacheManager.getBaseAddress(address);
        DCacheBlock block = null;
        int execution_cycles = 0;

        // update same address block, if not then free block , if not then lru-block
//        if (set.doesAddressExist(base_address)){
//            block = set.getAddressBlock(base_address);
//        }else
        if (set.hasFreeBlock()){
            block = set.getEmptyBlock(base_address);
            execution_cycles += DCacheManager.latency;
        }else{
            block = set.getLRUBlock();
            execution_cycles += DCacheManager.latency;
//            execution_cycles += DCacheManager.latency; // latency for eviction
        }
        if (block == null) throw new Exception("DCache cannot find a null block");

        block.base_address = base_address;
        block.dirty = store;
        set.toggleLRU(block);

        return execution_cycles;
    }

	public static void run() throws Exception{
		if(busy){
			remaining_latency--;
			if(remaining_latency == 0){
				write_block(processing_block_address, is_store);
				processing_block_address = 0;
				is_store = false;
				busy = false;
				prev_gid = -1;
				MIPS.print("DCache: Fetched from cache !!!");
			}
		}
	}

}
