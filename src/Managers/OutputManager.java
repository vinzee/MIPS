package Managers;

import java.util.ArrayList;
import java.util.Arrays;
import MIPS.MIPS;

public class OutputManager {
	public static ArrayList<int[]> output_table = new ArrayList<int[]>();	
	public static int last_output_index = -1;
	
	private static void printScoreboard() {
		System.out.println("--------Scoreboard:" + MIPS.cycle + "---------");
		for (int[] arr: output_table) {
		  System.out.println(Arrays.toString(arr));
		}
//		System.out.println("--------------------------");
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
