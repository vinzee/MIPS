import java.util.Arrays;

public class Instruction {
	private String opcode;
	private String[] operands;
	private String type;
	private String label;
	
	public Instruction(String label, String opcode, String[] operands, String type) {
		super();
		this.opcode = opcode;
		this.operands = operands;
		this.type = type;
		this.label = label;		
	}
	public String getOpcode() {
		return opcode;
	}
	public String[] getOperands() {
		return operands;
	}
	public String getType() {
		return type;
	}
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return opcode + " : " + Arrays.toString(operands); // + "; " + type;
	}
}
