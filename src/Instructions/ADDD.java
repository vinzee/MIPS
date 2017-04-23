package Instructions;

import java.util.ArrayList;

import Instructions.Operands.RegisterOperand;
import Managers.RegisterManager;

public class ADDD extends Instruction implements Executable{
	RegisterOperand register_operand1; // destination
	RegisterOperand register_operand2; // source 1
	RegisterOperand register_operand3; // source 2
			
	public ADDD(RegisterOperand register_operand1, RegisterOperand register_operand2, RegisterOperand register_operand3) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.register_operand3 = register_operand3;
	}
	
	@Override
	public void execute() throws Exception {
		double value = RegisterManager.read(this.register_operand2) + RegisterManager.read(this.register_operand3);
		RegisterManager.write(this.register_operand1, value);
	}

	@Override
	public boolean isDestinationBeingWritten() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areSourcesBeingWritten() throws Exception {
		// TODO Auto-generated method stub
		return false;
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