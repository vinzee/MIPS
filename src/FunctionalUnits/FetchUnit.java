package FunctionalUnits;

import Instructions.HLT;
import Instructions.Instruction;
import Stages.IssueStage;
import MIPS.*;

public class FetchUnit extends FunctionalUnit {
	public static final FetchUnit i = new FetchUnit(1);

	private FetchUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst, int gid) {
		if(inst instanceof HLT && MIPS.jump){
//			MIPS.jump = false;
		}else if(!MIPS.halt){
			FetchUnit.i.setBusy(true);
			IssueStage.gid_queue.add(gid);
		}
	}
}
