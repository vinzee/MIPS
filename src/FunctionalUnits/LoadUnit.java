package FunctionalUnits;

public class LoadUnit extends FunctionalUnit {
	public static final LoadUnit i = new LoadUnit(1);
	
	private LoadUnit(int latency) {
		super(latency);
	}
}
