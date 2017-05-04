package Cache;

public class DCacheBlock {
	int base_address;
	boolean dirty;
    int no_of_reads = 0;

	public DCacheBlock(int base_address) {
		this.base_address = base_address;
		this.dirty = false;
	}

	@Override
	public String toString() {
		return  Integer.toString(this.base_address);
	}
}
