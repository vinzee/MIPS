package FunctionalUnits;
import Instructions.Instruction;

public class ExecuteUnit extends FunctionalUnit {
	public static final ExecuteUnit i = new ExecuteUnit(1);
	
	private ExecuteUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst) {
		// TODO Auto-generated method stub
		
	}
}
