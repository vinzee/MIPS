package Instructions;

import java.util.ArrayList;

import Instructions.Operands.RegisterOperand;

// Data Transfers - LW, SW, L.D, S.D
// Arithmetic/ logical - DADD, DADDI, DSUB, DSUBI, AND, ANDI, OR, ORI, LI, LUI, ADD.D, MUL.D, DIV.D, SUB.D
// Control - J, BEQ, BNE
// Special purpose - HLT (to stop fetching new instructions)

public abstract class Instruction {
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public abstract void execute() throws Exception;
	public abstract RegisterOperand getDestinationRegister() throws Exception;
	public abstract ArrayList<RegisterOperand> getSourceRegisters() throws Exception;

	public void markRegisterStatus() throws Exception{
		RegisterOperand destination_operand = this.getDestinationRegister();
		if(destination_operand != null) destination_operand.setStatus("writing");

		for (RegisterOperand source_operand: this.getSourceRegisters()) {
			source_operand.setStatus("reading");
		}
	};

	public void unMarkRegisterStatus() throws Exception{
		RegisterOperand destination_operand = this.getDestinationRegister();
		if(destination_operand != null) destination_operand.setStatus(null);

		for (RegisterOperand source_operand: this.getSourceRegisters()) {
			source_operand.setStatus(null);
		}
	};

	public boolean isDestinationBeingRead() throws Exception{
		RegisterOperand destination_operand = this.getDestinationRegister();
		if(destination_operand != null) return destination_operand.isBeingRead();
		return false;
	};

	public boolean isDestinationBeingWritten() throws Exception{
		RegisterOperand destination_operand = this.getDestinationRegister();
		if(destination_operand != null) return destination_operand.isBeingWritten();
		return false;
	};

	public boolean areSourcesBeingWritten() throws Exception{
		for (RegisterOperand source_operand: this.getSourceRegisters()) {
			if(source_operand.isBeingWritten()) return true;
		}
		return false;		
	};
}
