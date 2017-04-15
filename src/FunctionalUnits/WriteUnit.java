package FunctionalUnits;
import Instructions.Instruction;

public class WriteUnit extends FunctionalUnit {
	public static final WriteUnit i = new WriteUnit(1);
	
	private WriteUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst) {
		// TODO Auto-generated method stub
		
	}
}
