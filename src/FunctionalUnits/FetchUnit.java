package FunctionalUnits;

import Instructions.Instruction;
import Stages.IssueStage;

public class FetchUnit extends FunctionalUnit {
	public static final FetchUnit i = new FetchUnit(1);
	
	private FetchUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst, int gid) {
		FetchUnit.i.setBusy(true);
		IssueStage.gid_queue.add(gid);
	}
}
