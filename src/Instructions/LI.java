package Instructions;

import java.util.ArrayList;

import Instructions.Operands.*;
import Managers.RegisterManager;

public class LI extends Instruction implements Memorable{
	RegisterOperand register_operand;
	ImmediateOperand immediate_operand;
	
	public LI(RegisterOperand ro, ImmediateOperand io) {
		this.register_operand = ro;
		this.immediate_operand = io;
	}

	@Override
	public void write() throws Exception {
		register_operand.setValue(immediate_operand.value);
		RegisterManager.write(register_operand, immediate_operand.value);
	}

	@Override
	public void execute() {
		// Do Nothing
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

}