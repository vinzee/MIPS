package Instructions.Operands;

import Managers.RegisterManager;

public class RegisterOperand extends Operand {
	public boolean floating_point;
	public int index;
	
	public RegisterOperand(String register_name) throws Exception {
		super();
		
		if(isValidRegister(register_name)){
			this.floating_point = (register_name.charAt(0) == 'r') ? false : true;
			this.index = getIndex(register_name);			
		}else{
			throw new Error("Invalid Register name - " + register_name);
		}
	}

	public double getValue() throws Exception {	
		return RegisterManager.read(this);
	}
	public void setValue(double value) throws Exception {	
		RegisterManager.write(this, value);
	}
	public boolean isBeingRead() throws Exception {	
		return RegisterManager.getReadStatus(this);
	}	
	public boolean isBeingWritten() throws Exception {	
		return RegisterManager.getWriteStatus(this);
	}	
	public void setReadStatus(boolean value) throws Exception {	
		RegisterManager.setReadStatus(this, value);
	}	
	public void setWriteStatus(boolean value) throws Exception {	
		RegisterManager.setWriteStatus(this, value);
	}	
	
	private static int getIndex(String register_name) throws Exception {	
		return Integer.parseInt(register_name.substring(1,register_name.length()));
	}

	public static boolean isValidRegister(String register_name) throws Exception {
		if(register_name.matches("[f|r]\\d+")){
			int index = getIndex(register_name);

			if(0 <= index && index < 32){
				return true;
			}else{
				return false;				
			}
		}else{
			return false;
		}
	}
	
	@Override
	public String toString() {
		return (floating_point == true ? "F" : "R") + "" + index;
	}

}
