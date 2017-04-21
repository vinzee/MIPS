package Instructions;

// Data Transfers - LW, SW, L.D, S.D
// Arithmetic/ logical - DADD, DADDI, DSUB, DSUBI, AND, ANDI, OR, ORI, LI, LUI, ADD.D, MUL.D, DIV.D, SUB.D
// Control - J, BEQ, BNE
// Special purpose - HLT (to stop fetching new instructions)

public class Instruction {
//	protected String[] operands = {};
	
//	public String[] getOperands() {
//		return operands;
//	}
	
//	public String parseOpcode() {
//		return "";
//	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName(); // + ":" + String.join(",", operands);
	}
}
