package Parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Managers.MemoryManager;

public class MemoryParser {

	public static void parse(String filepath) throws Exception {
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			Integer count = 0;
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

	private static void parseLine(String line, int count) {
		MemoryManager.writeMemoryAddress(MemoryManager.start_address + count, Integer.parseInt(line, 2));
	}
}
