package Managers;
import java.util.TreeMap;

public class MemoryManager {
	public static int start_address = 0x100;
	static TreeMap<Integer, Integer> memory = new TreeMap<Integer, Integer>();
	
	public static void writeMemoryAddress(int address, int value) {
		memory.put(address, value);
	}

	public static Integer readMemoryAddress(int address) {
		return memory.get(address);
	}

	private static void debug() {
		// TODO Auto-generated method stub
		System.out.println("Memory - " + memory);
	}
}
