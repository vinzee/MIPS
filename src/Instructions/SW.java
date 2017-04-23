package Instructions;

import java.util.ArrayList;

import Instructions.Operands.*;
import Managers.MemoryManager;

public class SW extends Instruction implements Executable, Memorable{
	RegisterOperand register_operand;
	MemoryOperand memory_operand;
	
	public SW(RegisterOperand ro, MemoryOperand mo) {
		this.register_operand = ro;
		this.memory_operand = mo;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
	}

	@Override
	public void write() throws Exception {
		MemoryManager.write(memory_operand.final_address(), "word", (int) register_operand.getValue());
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