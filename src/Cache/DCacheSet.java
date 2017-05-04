package Cache;

import java.util.Arrays;

public class DCacheSet {
	// 2 blocks in set
	DCacheBlock[] d_cache_blocks = new DCacheBlock[4];
    int lru = 0;

    public DCacheSet(){
    	d_cache_blocks[0] = new DCacheBlock(-1);
    	d_cache_blocks[1] = new DCacheBlock(-1);
    	d_cache_blocks[2] = new DCacheBlock(-1);
    	d_cache_blocks[3] = new DCacheBlock(-1);
    }

    public boolean does_address_exist(int base_address){
    	return ((d_cache_blocks[0].base_address == base_address) || (d_cache_blocks[1].base_address == base_address));
    }

    public DCacheBlock get_address_block(int base_address){
    	for(int i=0;i<4;i++){
            if (d_cache_blocks[i].base_address == base_address)
                return d_cache_blocks[i];
    	}
    	throw new Error("Dcache - block not found !");
    }

    public DCacheBlock get_empty_block(int base_address){
    	for(int i=0;i<4;i++){
            if (d_cache_blocks[i].base_address == -1)
                return d_cache_blocks[i];
    	}
        return null;
    }

    public DCacheBlock get_lru_block(){
    	int min = 999;
    	int min_i = -1;

    	for(int i=0;i<4;i++){
    		if(d_cache_blocks[i].no_of_reads < min){
    			min = d_cache_blocks[i].no_of_reads;
    			min_i = i;
    		}
    	}

        return d_cache_blocks[min_i];
    }

    @Override
	public String toString() {
		return Arrays.toString(d_cache_blocks);
	}
}
