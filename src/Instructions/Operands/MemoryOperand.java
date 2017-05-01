package Instructions.Operands;

public class MemoryOperand extends Operand {
	int base_address;
	public RegisterOperand base_register;
	int offset_address;
	int value;

	public MemoryOperand(String str) throws Exception {
		super();

		if(str.contains("(")){
			String offset = str.substring(0, str.indexOf("("));
			this.offset_address = Integer.parseInt(offset);
			String base = str.substring(str.indexOf("(") + 1, str.indexOf(")"));

			if(RegisterOperand.isValidRegister(base)){
				this.base_register = new RegisterOperand(base);
			}else{
				this.base_address = Integer.parseInt(base);
			}
		}else{
			this.base_address = Integer.parseInt(str);
		}
	}

	public int final_address() throws Exception {
		if(this.base_register != null){
			return (int)this.base_register.getValue() + this.offset_address;
		}else{
			return this.base_address + this.offset_address;
		}
	}

	@Override
	public String toString() {
		return offset_address + "(" + base_register + ")";
	}
}
