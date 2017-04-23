package Instructions;

import java.util.ArrayList;

import Instructions.Operands.RegisterOperand;

public class HLT extends Instruction implements Executable{
	@Override
	public void execute() {
		// Do Nothing
	}

	@Override
	public RegisterOperand getDestinationRegister() throws Exception {
		return null;
	}

	@Override
	public ArrayList<RegisterOperand> getSourceRegisters() throws Exception {
		ArrayList<RegisterOperand> source_registers = new ArrayList<RegisterOperand>();
		return source_registers;
	}

}