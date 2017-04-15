package Managers;
import java.util.Arrays;

public class RegisterManager {
	static double[] integer_registers = new double[32];
	static double[] floating_point_registers = new double[32];

	static String[] integer_register_status = new String[32];
	static String[] floating_point_register_status = new String[32];

//	Pattern int_register_pattern = Pattern.compile();
//	Pattern fp_register_pattern = Pattern.compile("f\\d+");
	private static int getIndex(String key) throws Exception {	
		return Integer.parseInt(key.substring(1,key.length()));
	}

	public static void validateKey(String key) throws Exception {
		if(key.matches("[f|r]\\d+")){
			int index = getIndex(key);

			if(!(0 <= index && index < 32))
				throw new Error("Invalid Register key - " + key);				
		}else{
			throw new Error("Invalid Register key - " + key);
		}
	}
	
	public static double read(String key) throws Exception {
		validateKey(key);
		int index = getIndex(key);
		
		if(key.charAt(0) == 'r'){
			return integer_registers[index-1];
		}else{ // assume its a f type
			return floating_point_registers[index-1];
		}
	}
	

	public static double write(String key, Double value) throws Exception {
		validateKey(key);		
		int index = getIndex(key);
		
		if(key.charAt(0) == 'r'){
			return integer_registers[index-1] = value;
		}else{ // assume its a f type
			return floating_point_registers[index-1] = value;
		}
	}
	
	public static void debug() throws Exception {
//		write("f31", 12.0);
//		System.out.println(read("f31"));
		System.out.println("Integer Registers - \n" + Arrays.toString(RegisterManager.integer_registers));
		System.out.println("\nFloating Point Registers - \n" + Arrays.toString(RegisterManager.floating_point_registers));
	}	
}
