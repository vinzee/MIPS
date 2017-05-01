package Managers;
import java.util.TreeMap;

public class MemoryManager {
	public static int start_address = 0x100;
	public static int end_address;
	public static TreeMap<Integer, Integer> memory = new TreeMap<Integer, Integer>();

	public static int getFormattedAddress(int address) {
		int new_address = ((address - 256) / 4 ) + 256;

		if(!memory.containsKey(new_address)) throw new Error("Invalid Memory address: " + address + " : " + new_address);

		return new_address;
	}

//	http://stackoverflow.com/questions/1735840/how-do-i-split-an-integer-into-2-byte-binary
	public static void write(int address, String type, int value) {
		address = getFormattedAddress(address);

		switch(type){
		case "word":
			memory.put(address, value);
			break;
		case "double":
			int value_0 = ((value >> 8) & 0xFF);
			int value_1 = (value & 0xFF);
			memory.put(address, value_0);
			memory.put(address+1, value_1);
			break;
		}
	}

	public static Integer read(int address, String type) {
		address = getFormattedAddress(address);

		int value = 0;

		switch(type){
		case "word":
			value = memory.get(address);
			break;
		case "double":
			int value_0 = memory.get(address);
			int value_1 = memory.get(address+1);
			value = ((value_0 << 8) | (value_1 & 0xFF));
			break;
		}

		return value;
	}

	public static void debug() {
		write(257,"double", 25);
		System.out.println(read(257,"double"));
		System.out.println("Memory - " + memory);
	}
}
