package Cache;

public class DCacheSet {
	// 2 blocks in set
	DCacheBlock[] d_cache_blocks = new DCacheBlock[2];
    int lru = 0;
    
    public DCacheSet(){
    	d_cache_blocks = new DCacheBlock[2];
    	d_cache_blocks[0] = new DCacheBlock(-1);
    	d_cache_blocks[1] = new DCacheBlock(-1);
    }
    
    public void toggleLRU(DCacheBlock block){
        lru = (d_cache_blocks[0].equals(block)) ? 1 : 0;
    }

    public boolean isFree(int id){
        return d_cache_blocks[id].isFree();
    }

    public boolean hasFreeBlock(){
        return isFree(0) || isFree(1);
    }

    public boolean doesAddressExist(int base_address){
        return (d_cache_blocks[0].base_address == base_address)
                || (d_cache_blocks[0].base_address == base_address);
    }

    public boolean isLRUBlockDirty(){
        return d_cache_blocks[lru].dirty;
    }

    public DCacheBlock getAddressBlock(int base_address){

        if (!doesAddressExist(base_address))
            return null;

        if (d_cache_blocks[0].base_address == base_address)
            return d_cache_blocks[0];
        return d_cache_blocks[1];
    }

    public DCacheBlock getEmptyBlock(int base_address){
        if (!hasFreeBlock())
            return null;
        if (d_cache_blocks[0].base_address == -1)
            return d_cache_blocks[0];
        return d_cache_blocks[1];
    }

    public DCacheBlock getLRUBlock(){
        return d_cache_blocks[lru];
    }
}
