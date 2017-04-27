package MIPS;
import java.util.HashMap;
import java.util.TreeMap;

import Instructions.Instruction;
import Managers.*;
import Parsers.*;
import Stages.*;

//	simulator inst.txt data.txt config.txt result.txt
public class MIPS {
	public static TreeMap<Integer, Instruction> instructions = new TreeMap<Integer, Instruction>();
	public static HashMap<String, Integer> label_map = new HashMap<String, Integer>();	

	public static boolean halt = false;
	public static int halt_count = 0;
	public static int cycle = 1;
	public static int MAX_CYCLES = 200;

	public static void main(String[] args) throws Exception {
		InstructionParser.parse(args[0]);
		MemoryParser.parse(args[1]);
		ConfigParser.parse(args[2]);
		OutputManager.init(args[3]);
		
		MIPS.execute_scoreboard();
	}
	
	// starting point
	static void execute_scoreboard() throws Exception{
		while(cycle < MAX_CYCLES){
			WriteStage.execute();
			ExecuteStage.execute();
			ReadOperandsStage.execute();
			IssueStage.execute();
			FetchStage.execute();
			OutputManager.printResults();

			if(halt) halt_count++;
			if(stop_machine()) break;

			cycle++;
		}
	}
	
	public static boolean stop_machine(){
		return halt && halt_count > 5;
	}
	
	public static void halt_machine(){
		halt = true;
		FetchStage.id = -1;		
	}
	
	public static void print(String msg){
		System.out.println((char)27 + "[1m" + msg + (char)27 + "[0m");
	}
}
