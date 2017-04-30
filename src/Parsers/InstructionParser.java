package Parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import Instructions.*;
import Instructions.Operands.*;
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
	
	private static void parseLine(String line, Integer count) throws Exception {
		// TODO Auto-generated method stub
		String label = null;
		if(line.contains(":")){
			String[] l = line.split(":");
			label = l[0].toUpperCase();			
			line = l[1];
		}
		String[] tokens = line.trim().split("[\\s]", 2);
		String opcode = tokens[0].trim().toUpperCase().replace(".", "");		
		String[] operands = new String[0];
		if(tokens.length > 1){
			operands = tokens[1].replaceAll("\\s+", "").trim().split(",");				
		}
		
		Instruction inst = createInstruction(label, opcode, operands);
		
		MIPS.instructions.put(count, inst);
		if(label != null) MIPS.label_map.put(label, count);
	}

	//	http://www.mrc.uidaho.edu/mrc/people/jff/digital/MIPSir.html
	private static Instruction createInstruction(String label, String opcode, String[] operands) throws Exception {
		Instruction inst = null;
		ImmediateOperand immediate_operand = null;
		MemoryOperand memory_operand = null;
		RegisterOperand register_operand1 = null, register_operand2 = null, register_operand3 = null;
		
		switch(opcode){
		case "LW": // Load Word
			register_operand1 = new RegisterOperand(operands[0]);
			memory_operand = new MemoryOperand(operands[1]);
			inst = new LW(register_operand1, memory_operand);
			break;
		case "LD": // Load Double
			register_operand1 = new RegisterOperand(operands[0]);
			memory_operand = new MemoryOperand(operands[1]);
			inst = new LD(register_operand1, memory_operand);
			break;
		case "LI": // Load Immediate
			register_operand1 = new RegisterOperand(operands[0]);
			immediate_operand = new ImmediateOperand(operands[1]);
			inst = new LI(register_operand1, immediate_operand);
			break;
		case "LUI": // Load upper Immediate
			register_operand1 = new RegisterOperand(operands[0]);
			immediate_operand = new ImmediateOperand(operands[1]);
			inst = new LUI(register_operand1, immediate_operand);
			break;
		case "SW": // Store Word
			register_operand1 = new RegisterOperand(operands[1]);
			memory_operand = new MemoryOperand(operands[0]);
			inst = new SW(register_operand1, memory_operand);
			break;
		case "SD":
			register_operand1 = new RegisterOperand(operands[1]);
			memory_operand = new MemoryOperand(operands[0]);
			inst = new SD(register_operand1, memory_operand);
			break;
		case "DADD":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			register_operand3 = new RegisterOperand(operands[2]);
			inst = new DADD(register_operand1, register_operand2, register_operand3);
			break;
		case "DADDI":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			immediate_operand = new ImmediateOperand(operands[2]);
			inst = new DADDI(register_operand1, register_operand2, immediate_operand);
			break;
		case "DSUB":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			register_operand3 = new RegisterOperand(operands[2]);
			inst = new DSUB(register_operand1, register_operand2, register_operand3);
			break;
		case "DSUBI":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			immediate_operand = new ImmediateOperand(operands[2]);
			inst = new DSUBI(register_operand1, register_operand2, immediate_operand);
			break;
		case "AND":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			register_operand3 = new RegisterOperand(operands[2]);
			inst = new AND(register_operand1, register_operand2, register_operand3);
			break;
		case "ANDI":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			immediate_operand = new ImmediateOperand(operands[2]);
			inst = new ANDI(register_operand1, register_operand2, immediate_operand);
			break;
		case "OR":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			register_operand3 = new RegisterOperand(operands[2]);
			inst = new OR(register_operand1, register_operand2, register_operand3);
			break;
		case "ORI":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			immediate_operand = new ImmediateOperand(operands[2]);
			inst = new ORI(register_operand1, register_operand2, immediate_operand);
			break;
		case "ADDD":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			register_operand3 = new RegisterOperand(operands[2]);
			inst = new ADDD(register_operand1, register_operand2, register_operand3);
			break;
		case "MULD":
		case "MULTD":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			register_operand3 = new RegisterOperand(operands[2]);
			inst = new MULD(register_operand1, register_operand2, register_operand3);
			break;
		case "DIVD":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			register_operand3 = new RegisterOperand(operands[2]);
			inst = new DIVD(register_operand1, register_operand2, register_operand3);
			break;
		case "SUBD":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			register_operand3 = new RegisterOperand(operands[2]);
			inst = new SUBD(register_operand1, register_operand2, register_operand3);
			break;
		case "J":
			inst = new J(operands[0]);
			break;
		case "BEQ":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			inst = new BEQ(register_operand1, register_operand2, operands[2]);
			break;
		case "BNE":
			register_operand1 = new RegisterOperand(operands[0]);
			register_operand2 = new RegisterOperand(operands[1]);
			inst = new BNE(register_operand1, register_operand2, operands[2]);
			break;
		case "HLT":
			inst = new HLT();
			break;
		default:
			throw new Error("Invalid Opcode: " + opcode);
		}
		inst.label = label;
		
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