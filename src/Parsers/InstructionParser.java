package Parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import Instructions.*;
import MIPS.MIPS;

public class InstructionParser {
	public static void parse(String filepath) throws Exception {
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			int count = 0;

			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				parseLine(line, count);
				count += 1;
			}
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
		String[] tokens = line.trim().split("[\\s]", 2);
		String opcode = tokens[0].trim().toUpperCase().replace(".", "");		
		String[] operands = null;
		if(tokens.length > 1){
			operands = tokens[1].replaceAll("\\s+", "").trim().split(",");				
		}
		
		Instruction inst = createInstruction(opcode, operands);
		
		MIPS.instructions.put(count, inst);
		if(label != null){
			MIPS.label_map.put(label, count);			
		}
	}

	private static Instruction createInstruction(String opcode, String[] operands) {
		Instruction inst = null;
		
		switch(opcode){
		case "LD":
			inst = new LD(operands);
			break;
		case "LI":
			inst = new LI(operands);
			break;
		case "HLT":
			inst = new HLT();
			break;
		default:
			throw new Error("Invalid Opcode !");
		}
		
		return inst;
	}

	public static void debug(){
		for(Map.Entry<Integer, Instruction> entry : MIPS.instructions.entrySet()) {
			Integer no = entry.getKey();
			Instruction i = entry.getValue();

		  System.out.println(no + " => " + i);
		}
		System.out.println("\n\nLabels - " + MIPS.label_map);
	}
}