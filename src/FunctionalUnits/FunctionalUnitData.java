package FunctionalUnits;

public class FunctionalUnitData{	
	public int inst_index;
	public FunctionalUnit unit;
	public int total_latency;	
	public int remaining_latency;	
	public boolean is_executing;
	public int output_index;
	
	public FunctionalUnitData(int inst_index, FunctionalUnit unit, int total_latency, int output_index){
		this.inst_index = inst_index;
		this.unit = unit;
		this.total_latency = this.remaining_latency = total_latency;
		this.output_index = output_index;
	}
}
