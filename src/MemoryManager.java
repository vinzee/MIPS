import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class MemoryManager {
	static int start_address = 0x100;
	static TreeMap<Integer, Integer> memory = new TreeMap<Integer, Integer>();

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

	private static void parseLine(String line, int count) {
		writeMemoryAddress(start_address + count, Integer.parseInt(line, 2));
	}
	
	private static void writeMemoryAddress(int address, int value) {
		memory.put(address, value);
	}

	private static Integer readMemoryAddress(int address) {
		return memory.get(address);
	}

	private static void debug() {
		// TODO Auto-generated method stub
		System.out.println("Memory - " + memory);
	}
}
