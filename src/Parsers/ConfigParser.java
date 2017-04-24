package Parsers;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import FunctionalUnits.*;

public class ConfigParser {
	public static void parse(String filepath) throws Exception {
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				parseLine(line);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private static void parseLine(String line) throws Exception {
		String[] l = line.split(":");
		String[] m = l[1].split(",");
		int no = Integer.parseInt(m[0].trim());

		switch(l[0]){
			case "fp adder":
				for(int i=0;i<no;i++){
					FpAdderUnit unit = new FpAdderUnit(Integer.parseInt(m[1].trim()));
					ExecutionUnit.fp_adder_unit_pool.add(unit);
				}
				break;
			case "fp multiplier":
				for(int i=0;i<no;i++){
					FpMultiplierUnit unit = new FpMultiplierUnit(Integer.parseInt(m[1].trim()));
					ExecutionUnit.fp_multiplier_unit_pool.add(unit);
				}
				break;
			case "fp divider":
				for(int i=0;i<no;i++){
					FpDividerUnit unit = new FpDividerUnit(Integer.parseInt(m[1].trim()));
					ExecutionUnit.fp_divider_unit_pool.add(unit);
				}
				break;
			case "i-cache":
				// TODO
				break;
			default:
				throw new Exception("Unhandled config type - " + l[0]);
		}
		
	}
}