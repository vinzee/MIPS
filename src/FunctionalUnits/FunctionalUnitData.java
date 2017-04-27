package FunctionalUnits;

public class FunctionalUnitData{	
	public FunctionalUnit unit;
	public int id;
	public int gid;
	public int remaining_latency;	
	public boolean is_executing;
	
	public FunctionalUnitData(int id, int gid, FunctionalUnit unit, int total_latency){
		this.id = id;
		this.gid = gid;
		this.unit = unit;
		this.remaining_latency = total_latency;
	}
}
