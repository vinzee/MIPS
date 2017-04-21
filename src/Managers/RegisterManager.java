package Managers;
import java.util.Arrays;

import Instructions.Operands.RegisterOperand;

public class RegisterManager {
	static double[] integer_registers = new double[32];
	static double[] floating_point_registers = new double[32];

	static String[] integer_register_status = new String[32];
	static String[] floating_point_register_status = new String[32];
	
	public static double read(RegisterOperand register_operand) throws Exception {
		if(register_operand.floating_point){
			return floating_point_registers[register_operand.index-1];
		}else{
			return integer_registers[register_operand.index-1];
		}
	}

	public static double write(RegisterOperand register_operand, Double value) throws Exception {
		if(register_operand.floating_point){
			return floating_point_registers[register_operand.index-1] = value;
		}else{ // assume its a f type
			return integer_registers[register_operand.index-1] = value;
		}
	}
	
	public static void debug() throws Exception {
//		write("f31", 12.0);
//		System.out.println(read("f31"));
		System.out.println("Integer Registers - \n" + Arrays.toString(RegisterManager.integer_registers));
		System.out.println("\nFloating Point Registers - \n" + Arrays.toString(RegisterManager.floating_point_registers));
	}	
}
