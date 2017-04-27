package FunctionalUnits;
import Instructions.Instruction;
import Managers.RegisterManager;

public class WriteUnit extends FunctionalUnit {
	public static final WriteUnit i = new WriteUnit(1);
	
	private WriteUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst) throws Exception {
		inst.write();
		RegisterManager.push_cache_to_registers();
	}
}
