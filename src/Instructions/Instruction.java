package Instructions;

import java.util.ArrayList;

import Instructions.Operands.*;

// Data Transfers - LW, SW, L.D, S.D
// Arithmetic/ logical - DADD, DADDI, DSUB, DSUBI, AND, ANDI, OR, ORI, LI, LUI, ADD.D, MUL.D, DIV.D, SUB.D
// Control - J, BEQ, BNE
// Special purpose - HLT (to stop fetching new instructions)

public abstract class Instruction {
	public abstract void execute() throws Exception;
	public abstract void write() throws Exception;
	public abstract RegisterOperand getDestinationRegister() throws Exception;
	public abstract ArrayList<RegisterOperand> getSourceRegisters() throws Exception;
	public abstract MemoryOperand getMemoryOperand() throws Exception;
	public abstract ImmediateOperand getImmediateOperand() throws Exception;
	public String label;
	public String i;

	@Override
	public String toString() {
		try {
			StringBuilder s =  new StringBuilder((this.label == null ? "" : this.label + ":") + this.getClass().getSimpleName());
			if(this.getDestinationRegister() != null) s.append(" " + this.getDestinationRegister().toString());

			for (RegisterOperand source_operand: this.getSourceRegisters())
				s.append(", " + source_operand);
			if(this.getMemoryOperand() != null) s.append(", " + this.getMemoryOperand().toString());
			if(this.getImmediateOperand() != null) s.append(", " + this.getImmediateOperand().toString());

			return s.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void markDestinationRegisterStatus(int value) throws Exception{
		if(this.getDestinationRegister() != null) this.getDestinationRegister().setWriteStatus(value);
	}

	public void unMarkRegisterStatus() throws Exception{
		RegisterOperand destination_operand = this.getDestinationRegister();
		if(destination_operand != null) destination_operand.setWriteStatus(0);
	};

	public boolean isDestinationBeingWritten(int gid) throws Exception{
		RegisterOperand destination_operand = this.getDestinationRegister();
		if(destination_operand != null) return destination_operand.isBeingWritten(gid);
		return false;
	};

	public boolean areSourcesBeingWritten(int gid) throws Exception{
		for (RegisterOperand source_operand: this.getSourceRegisters()) {
			if(source_operand.isBeingWritten(gid)) return true;
		}
		return false;
	};
}
