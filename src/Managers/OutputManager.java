package Managers;

import java.util.ArrayList;

import MIPS.MIPS;

public class OutputManager {
	public static ArrayList<int[]> output_table = new ArrayList<int[]>();	
	public static int last_gid = -1;
	public static final String instructionOutputFormatString = " %-2s %-15s  %-4s  %-4s  %-4s  %-4s  %-3s  %-3s  %-3s  %-3s "; // %-6s 

	// 0		1  2  3  4  5  6    7    8		 9
	// inst_no, F, I, R, E, W, RAW, WAW, struct, WAR
	private static void printScoreboard() {
		System.out.println("----------------------Scoreboard:" + MIPS.cycle + "-----------------------");
		System.out.println(String.format(instructionOutputFormatString, "#", "Instruction", "FT", "IS", "RO", "EX", "WB", "RAW", "WAW", "Struct")); // , "WAR"
		for (int[] arr: output_table) {
		  System.out.println(String.format(instructionOutputFormatString, arr[0], MIPS.instructions.get(arr[0]), arr[1], arr[2], arr[3], arr[4], arr[5], arr[6] == 1 ? 'Y' : 'N', arr[7] == 1 ? 'Y' : 'N', arr[8] == 1 ? 'Y' : 'N')); // , arr[9] == 1 ? 'Y' : 'N'
		}
		System.out.println("--------------------------------------------------------------------------");
	}
	
	public static int read(int gid, int col) {
		return output_table.get(gid)[col];
	}

	public static void write(int gid, int col, int value) {
		if(output_table.get(gid)[col] != 0) throw new Error("Overwrite in output output_table[" + gid + "," + col + "]" + " = " + value);
		write_silent(gid, col, value);
	}
	
	public static void write_silent(int gid, int col, int value) {
		output_table.get(gid)[col] = value;		
	}
	
	public static void printResults() {
		printScoreboard();
	}

	public static int add() {
		output_table.add(new int[9]);
		return ++last_gid;
	}

	public static void init(String string) {
		// create file to store results
	}

}
