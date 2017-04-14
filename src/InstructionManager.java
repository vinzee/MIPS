import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class InstructionManager {
	static TreeMap<Integer, Instruction> instructions = new TreeMap<Integer, Instruction>();
	static HashMap<String, Integer> labels = new HashMap<String, Integer>();	
	
	public static void parse(String filepath) throws Exception {
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			Integer count = 0;
			while ((line = br.readLine()) != null) {
				// process the line.
				line = line.trim().toLowerCase();
//				System.out.println(line);
				parseLine(line, count);
				count += 1;
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
	
	private static void parseLine(String line, Integer count) {
		// TODO Auto-generated method stub
		String label = null;
		if(line.contains(":")){
			String[] l = line.split(":");
			label = l[0];			
			line = l[1];
		}
		String[] tokens = line.split("[\\s]", 2);
		String opcode = tokens[0].trim(); // .toUpperCase();
		
		String[] operands = Arrays.copyOfRange(tokens, 1, tokens.length);

//		System.out.println("--- " + label + " : " + opcode + " " + Arrays.asList(operands));
		
		Instruction inst = new Instruction(label, opcode, operands, "my_type");
		instructions.put(count, inst);
		if(label != null){
			labels.put(label, count);			
		}
	}

	public static void debug(){
		for(Map.Entry<Integer, Instruction> entry : instructions.entrySet()) {
			Integer no = entry.getKey();
			Instruction i = entry.getValue();

		  System.out.println(no + " => " + i);
		}
		System.out.println("\n\nLabels - " + labels);
	}
}