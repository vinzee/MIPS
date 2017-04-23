package Instructions;

import java.util.ArrayList;

import Instructions.Operands.*;
import Managers.RegisterManager;

public class LUI extends Instruction implements Executable, Memorable{
	RegisterOperand register_operand;
	ImmediateOperand immediate_operand;
	
	public LUI(RegisterOperand ro, ImmediateOperand io) {
		this.register_operand = ro;
		this.immediate_operand = io;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write() throws Exception {
		register_operand.setValue(immediate_operand.value);
		RegisterManager.write(register_operand, immediate_operand.value);
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