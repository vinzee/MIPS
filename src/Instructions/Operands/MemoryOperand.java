package Instructions.Operands;

public class MemoryOperand extends Operand {
	int base_address;
	RegisterOperand base_register;
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
				this.base_address = this.base_register.index;
			}else{
				this.base_address = Integer.parseInt(base);				
			}			
		}else{
			this.base_address = Integer.parseInt(str);			
		}
	}

	public int final_address() {
		return this.base_address + this.offset_address;
	}
	
}
