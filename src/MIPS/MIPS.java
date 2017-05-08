package MIPS;
import java.util.HashMap;
import java.util.TreeMap;

import Cache.CacheManager;
import FunctionalUnits.ExecutionUnit;
import Instructions.Instruction;
import Managers.*;
import Parsers.*;
import Stages.*;

//	simulator inst.txt data.txt config.txt result.txt
public class MIPS {
	public static TreeMap<Integer, Instruction> instructions = new TreeMap<Integer, Instruction>();
	public static HashMap<String, Integer> label_map = new HashMap<String, Integer>();

	public static boolean halt = false;
	public static boolean jump = false;
	public static int post_halt_count = 0;
	public static int cycle = 1;
	public static final int MAX_CYCLES = 1000;
	public static final boolean CACHING_ENABLED = true;
	public static final boolean LOGGING_ENABLED = true;

	public static void main(String[] args) throws Exception {
		InstructionParser.parse(args[0]);
		MemoryParser.parse(args[1]);
		ConfigParser.parse(args[2]);
		OutputManager.init(args[3]);
		CacheManager.init();

		MIPS.execute_scoreboard();
	}

	// starting point
	static void execute_scoreboard() throws Exception{
		while(cycle < MAX_CYCLES){
			CacheManager.run();
			WriteStage.execute();
			ExecuteStage.execute();
			ReadOperandsStage.execute();
			IssueStage.execute();
			FetchStage.execute();

			if(LOGGING_ENABLED) OutputManager.printResults();

			if(stop_machine()) break;

			cycle++;
		}
		OutputManager.printFile();
	}

	public static boolean stop_machine(){
		if(!halt) return false;

		return IssueStage.gid_queue.isEmpty() &&
				ReadOperandsStage.gid_queue.isEmpty() &&
				ExecuteStage.gid_queue.isEmpty() &&
				ExecutionUnit.busy_units.isEmpty() &&
				WriteStage.gid_queue.isEmpty();
	}

	public static void halt_machine(){
		halt = true;
		MIPS.print("Halt Machine !!!");
	}

	public static void print(String msg){
		System.out.println((char)27 + "[1m" + msg + (char)27 + "[0m");
	}
}
