package Cache;

import Instructions.*;
import Instructions.Operands.MemoryOperand;

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

	public static void init(){
		d_cache_sets = new DCacheSet[no_of_sets];

		for(int i=0;i<no_of_sets;i++){
			d_cache_sets[i] = new DCacheSet();
		}

		latency = no_of_sets * LATENCY_PER_WORD;
	}

	private static DCacheSet getSet(int address){
		int set_id = address & 0b10000;
		set_id = set_id >> 4;
		return d_cache_sets[set_id];
	}

	private static int getBaseAddress(int address){
        int base_address = address >> 2;
        base_address = base_address << 2;
        return base_address;
    }

//	set.isLRUBlockDirty()

    public static void write_block(DCacheSet set, int base_address, boolean store) throws Exception {
        DCacheBlock block = null;
        // update same address block, if not then free block , if not then lru-block
        if (set.doesAddressExist(base_address)){
            block = set.getAddressBlock(base_address);
        }else if (set.hasFreeBlock()){
            block = set.getEmptyBlock(base_address);
        }else{
            block = set.getLRUBlock();
        }
        if (block == null) throw new Exception("DCache cannot find a null block");

        block.base_address = base_address;
        block.dirty = store;
        set.toggleLRU(block);
    }

	public static boolean is_available(int gid, Instruction inst) throws Exception {
		boolean is_load = (inst instanceof LD) || (inst instanceof LW);
		boolean is_store = (inst instanceof SD) || (inst instanceof SW);

		if(!is_load && !is_store) return true;

		MemoryOperand mo = inst.getMemoryOperand();
		int address = mo.final_address();
		DCacheSet set = getSet(address);
        int base_address = getBaseAddress(address);

        if(prev_gid != -1 && prev_gid != gid && busy){
        	throw new Error("Currently processing diff request in DCacheManager: " + prev_gid);
        }
//    	throw new Error("Same inst again in the DCacheManager: " + gid);

        if(busy && !ICacheManager.busy){
			remaining_latency--;
			if(remaining_latency == 0) busy = false;
			return false;
        }else{
            prev_gid = gid;

    		if(set.doesAddressExist(base_address)){ // cache hit
    			return true;
    		}else if(!ICacheManager.busy){ // cache miss
    			write_block(set, base_address, is_store);
    			busy = true;
    			remaining_latency = latency;
    			remaining_latency--;
    			return false;
    		}else{
    			return false;
    		}
        }
	}
}
