package FunctionalUnits;

public class IntegerUnit extends FunctionalUnit {
	public static final IntegerUnit i = new IntegerUnit(1);
	
	private IntegerUnit(int latency) {
		super(latency);
	}
}
