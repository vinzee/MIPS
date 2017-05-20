package FunctionalUnits;

import Instructions.HLT;
import Instructions.Instruction;
import Stages.FetchStage;
import Stages.IssueStage;
import MIPS.*;

public class FetchUnit extends FunctionalUnit {
	public static final FetchUnit i = new FetchUnit(1);

	private FetchUnit(int latency) {
		super(latency);
	}

	public void execute(Instruction inst, int gid) {
		if(MIPS.pre_jump) FetchStage.insts_processed_after_jump += 1;
		if(MIPS.jump){
//			MIPS.jump = false;
		}else if(!MIPS.halt){
			FetchUnit.i.setBusy(true);
			IssueStage.gid_queue.add(gid);
		}
	}
}
