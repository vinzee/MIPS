package Cache;

import java.util.Arrays;

public class DCacheSet {
	// 2 blocks in set
	DCacheBlock[] d_cache_blocks = new DCacheBlock[2];
    int lru = 0;

    public DCacheSet(){
    	d_cache_blocks = new DCacheBlock[2];
    	d_cache_blocks[0] = new DCacheBlock(-1);
    	d_cache_blocks[1] = new DCacheBlock(-1);
    }

    public void toggle_lru(DCacheBlock block){
        lru = (d_cache_blocks[0].equals(block)) ? 1 : 0;
    }

    public boolean has_free_block(){
        return d_cache_blocks[0].base_address == -1 || d_cache_blocks[1].base_address == -1;
    }

    public boolean does_address_exist(int base_address){
    	return ((d_cache_blocks[0].base_address == base_address) || (d_cache_blocks[1].base_address == base_address));
    }

    public DCacheBlock get_address_block(int base_address){
        if (d_cache_blocks[0].base_address == base_address)
            return d_cache_blocks[0];
        else
        	return d_cache_blocks[1];
    }

    public DCacheBlock get_empty_block(int base_address){
        if (d_cache_blocks[0].base_address == -1)
            return d_cache_blocks[0];
    	else if (d_cache_blocks[1].base_address == -1)
            return d_cache_blocks[1];
        return null;
    }

    public DCacheBlock get_lru_block(){
        return d_cache_blocks[lru];
    }

    @Override
	public String toString() {
		return Arrays.toString(d_cache_blocks);
	}
}
