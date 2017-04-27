package Instructions;

import java.util.ArrayList;

import Instructions.Operands.ImmediateOperand;
import Instructions.Operands.MemoryOperand;
import Instructions.Operands.RegisterOperand;

public class J extends Instruction{
	public String label;
	
	public J(String label) {
		super();
		this.label = label;
	}

	@Override
	public void execute() throws Exception {
		// Do Nothing
	}

	@Override
	public RegisterOperand getDestinationRegister() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<RegisterOperand> getSourceRegisters() throws Exception {
		ArrayList<RegisterOperand> source_registers = new ArrayList<RegisterOperand>();
		return source_registers;
	}

	@Override
	public void write() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MemoryOperand getMemoryOperand() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmediateOperand getImmediateOperand() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}