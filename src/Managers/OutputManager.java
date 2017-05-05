package Managers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import Cache.CacheManager;
import MIPS.MIPS;

public class OutputManager {
	public static ArrayList<int[]> output_table = new ArrayList<int[]>();
	public static int last_gid = -1;
	public static final String instructionOutputFormatString = " %-2s %-2s %-20s  %-4s  %-4s  %-4s  %-4s  %-3s  %-3s  %-3s  %-5s %-4s ";
	public static final String instructionPrintFormatString = " %-25s  %-4s  %-4s  %-4s  %-4s  %-3s  %-3s  %-3s  %-1s";
	public static String file_name;

	public static void init(String fname) {
		file_name = fname;
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
		System.out.println("----------------------Scoreboard:" + MIPS.cycle + "-----------------------");
		System.out.println(String.format(instructionOutputFormatString, "#", "#", "Instruction", "FT", "IS", "RO", "EX", "WB", "RAW", "WAW", "Struct", "EX"));
		int i=0;
		for (int[] arr: output_table) {
		  System.out.println(String.format(instructionOutputFormatString, i++, arr[0], MIPS.instructions.get(arr[0]), arr[1], arr[2], arr[3], arr[4], arr[5], arr[6] == 1 ? 'Y' : 'N', arr[7] == 1 ? 'Y' : 'N', arr[8] == 1 ? 'Y' : 'N', arr[9]));
		}
		System.out.println("\nICache:: Requests: " + CacheManager.icache_requests + " , Hits: " + CacheManager.icache_hits + " , Misses: " + CacheManager.icache_misses);
		System.out.println("DCache:: Requests: " + CacheManager.dcache_requests+ " , Hits: " + CacheManager.dcache_hits + " , Misses: " + CacheManager.dcache_misses);
		System.out.println("--------------------------------------------------------------------------");
	}

	public static void printFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter file = new PrintWriter(file_name, "UTF-8");

		file.println(String.format(instructionPrintFormatString, "Instruction", "FT", "IS", "RO", "EX", "WB", "RAW", "WAW", "Struct"));
		for (int[] arr: output_table) {
			file.println(String.format(instructionPrintFormatString, MIPS.instructions.get(arr[0]), arr[1], arr[2], arr[3], arr[4], arr[5], arr[6] == 1 ? 'Y' : 'N', arr[7] == 1 ? 'Y' : 'N', arr[8] == 1 ? 'Y' : 'N')); // , arr[9] == 1 ? 'Y' : 'N'
		}
		file.println("\nICache:: Requests: " + CacheManager.icache_requests + " , Hits: " + CacheManager.icache_hits);
		file.println("DCache:: Requests: " + CacheManager.dcache_requests+ " , Hits: " + CacheManager.dcache_hits);

		file.close();
	}

	public static int add() {
		output_table.add(new int[10]);
		return ++last_gid;
	}
}
