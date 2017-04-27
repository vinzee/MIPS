package Instructions;

import java.util.ArrayList;

import Instructions.Operands.*;
import Managers.RegisterManager;

public class ORI extends Instruction{
	RegisterOperand register_operand1;
	RegisterOperand register_operand2;
	ImmediateOperand immediate_operand;
	
	public ORI(RegisterOperand register_operand1, RegisterOperand register_operand2, ImmediateOperand immediate_operand) {
		super();
		this.register_operand1 = register_operand1;
		this.register_operand2 = register_operand2;
		this.immediate_operand = immediate_operand;
	}

	@Override
	public void execute() throws Exception {
		double value = (int) RegisterManager.read(this.register_operand2) | (int) this.immediate_operand.value;
		RegisterManager.write(this.register_operand1, value);
	}

	@Override
	public RegisterOperand getDestinationRegister() throws Exception {
		return this.register_operand1;
	}

	@Override
	public ArrayList<RegisterOperand> getSourceRegisters() throws Exception {
		ArrayList<RegisterOperand> source_registers = new ArrayList<RegisterOperand>();
		source_registers.add(this.register_operand2);
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
		return this.immediate_operand;
	}

}