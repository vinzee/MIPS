package Instructions;

import java.util.ArrayList;

import Instructions.Operands.*;
import Managers.MemoryManager;

public class SD extends Instruction implements Memorable{
	RegisterOperand register_operand;
	MemoryOperand memory_operand;
	
	public SD(RegisterOperand ro, MemoryOperand mo) {
		this.register_operand = ro;
		this.memory_operand = mo;
	}

	@Override
	public void write() throws Exception {
		MemoryManager.write(memory_operand.final_address(), "double", (int) register_operand.getValue());
	}

	@Override
	public void execute() throws Exception {
		// Do Nothing
	}

	@Override
	public RegisterOperand getDestinationRegister() throws Exception {
		return null;
	}

	@Override
	public ArrayList<RegisterOperand> getSourceRegisters() throws Exception {
		ArrayList<RegisterOperand> source_registers = new ArrayList<RegisterOperand>();
		source_registers.add(this.register_operand);
		return source_registers;
	}
}