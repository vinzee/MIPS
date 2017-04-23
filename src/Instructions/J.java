package Instructions;

import java.util.ArrayList;

import Instructions.Operands.RegisterOperand;

public class J extends Instruction{
	public String label;
	
	public J(String label) {
		super();
		this.label = label;
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		
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