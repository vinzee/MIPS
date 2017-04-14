import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {
	static HashMap<String, Integer> latencies = new HashMap<String, Integer>();
	static HashMap<String, Integer> no_of_function_units = new HashMap<String, Integer>();
	
	public static void parse(String filepath) throws Exception {
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			while ((line = br.readLine()) != null) {
				// process the line.
				line = line.trim().toLowerCase();
				System.out.println(line);
				parseLine(line);
			}
			
			debug();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private static void parseLine(String line) throws Exception {
		String[] l = line.split(":");

		switch(l[0]){
			case "fp adder":
			case "fp multiplier":
			case "fp divider":
				String[] m = l[1].split(",");
				no_of_function_units.put(l[0], Integer.parseInt(m[0].trim()));
				latencies.put(l[0], Integer.parseInt(m[1].trim()));						
				break;
			case "i-cache":
				// TODO
				break;
			default:
				throw new Exception("Unhandled config type - " + l[0]);
		}
		
	}

	public static void debug(){
		System.out.println("Latencies - " + latencies);
		System.out.println("FU - " + no_of_function_units);
	}
}