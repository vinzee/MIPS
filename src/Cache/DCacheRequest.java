package Cache;

public class DCacheRequest {
	int gid;
	int address;
	int base_address;
	DCacheSet set;
	DCacheBlock block;
	boolean store;
	int remaining_latency;
	int total_latency;

	public DCacheRequest(int gid, int address, int base_address, DCacheSet set, DCacheBlock block, boolean store, int total_latency) {
		super();
		this.gid = gid;
		this.address = address;
		this.base_address = base_address;
		this.set = set;
		this.block = block;
		this.store = store;
		this.remaining_latency = this.total_latency = total_latency;
	}
}
