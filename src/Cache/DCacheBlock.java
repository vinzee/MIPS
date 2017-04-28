package Cache;

public class DCacheBlock {
	int base_address;
	boolean dirty;

	public DCacheBlock(int base_address) {
		this.base_address = base_address;
		this.dirty = false;
	}
	
	 public boolean isFree(){
        return (base_address == -1);
    }
}
