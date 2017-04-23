package Instructions;

import java.util.ArrayList;

import Instructions.Operands.*;
import Managers.MemoryManager;
import Managers.RegisterManager;

public class LW extends Instruction implements Executable, Memorable{
	RegisterOperand register_operand;
	MemoryOperand memory_operand;
	
	public LW(RegisterOperand ro, MemoryOperand mo) {
		this.register_operand = ro;
		this.memory_operand = mo;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write() throws Exception {
		double value = MemoryManager.read(memory_operand.final_address(), "word");
		register_operand.setValue(value);
		RegisterManager.write(register_operand, value);
	}

		@Override
	public RegisterOperand getDestinationRegister() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<RegisterOperand> getSourceRegisters() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}