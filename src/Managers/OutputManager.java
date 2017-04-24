package Managers;

import java.util.ArrayList;
import MIPS.MIPS;

public class OutputManager {
	public static ArrayList<int[]> output_table = new ArrayList<int[]>();	
	public static int last_output_index = -1;
	public static final String instructionOutputFormatString = " %-15s  %-4s  %-4s  %-4s  %-4s  %-3s  %-3s  %-3s %-3s %-6s ";

	private static void printScoreboard() {
		System.out.println("----------------------Scoreboard:" + MIPS.cycle + "-----------------------");
		System.out.println(String.format(instructionOutputFormatString, "Instruction", "FT", "IS", "RO", "EX", "WB", "RAW", "WAW", "Struct", "WAR"));
		for (int[] arr: output_table) {
		  System.out.println(String.format(instructionOutputFormatString, MIPS.instructions.get(arr[0]), arr[1], arr[2], arr[3], arr[4], arr[5], arr[6] == 1 ? 'Y' : 'N', arr[7] == 1 ? 'Y' : 'N', arr[8] == 1 ? 'Y' : 'N', arr[9] == 1 ? 'Y' : 'N'));
		}
	}
	
	public static void printResults() {
		printScoreboard();
	}

	public static void add() {
		// 0		1  2  3  4  5  6    7    8		 9
		// inst_no, F, I, R, E, W, RAW, WAW, struct, WAR
		output_table.add(new int[10]);
		last_output_index++;
	}

	public static void init(String string) {
		// create file to store results
	}

}
