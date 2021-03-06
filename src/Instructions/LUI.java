package Instructions;

import java.util.ArrayList;

import Instructions.Operands.*;
import Managers.RegisterManager;

public class LUI extends Instruction{
	RegisterOperand register_operand;
	ImmediateOperand immediate_operand;
	
	public LUI(RegisterOperand ro, ImmediateOperand io) {
		this.register_operand = ro;
		this.immediate_operand = io;
	}

	@Override
	public void execute() {
		// Do Nothing
	}

	@Override
	public void write() throws Exception {
		register_operand.setValue(immediate_operand.value);
		RegisterManager.write(register_operand, immediate_operand.value);
	}

	@Override
	public RegisterOperand getDestinationRegister() throws Exception {
		return this.register_operand;
	}

	@Override
	public ArrayList<RegisterOperand> getSourceRegisters() throws Exception {
		ArrayList<RegisterOperand> source_registers = new ArrayList<RegisterOperand>();
		return source_registers;
	}

	@Override
	public MemoryOperand getMemoryOperand() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmediateOperand getImmediateOperand() throws Exception {
		return this.immediate_operand;
	}

}