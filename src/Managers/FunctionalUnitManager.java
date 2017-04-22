package Managers;

import java.util.ArrayList;
import java.util.HashMap;
import FunctionalUnits.*;
import Instructions.Instruction;

public class FunctionalUnitManager {
	HashMap<FunctionalUnit, Integer> status = new HashMap<FunctionalUnit, Integer>();
	
//	public static ArrayList<IntegerUnit> integer_unit_pool = new ArrayList<IntegerUnit>(); 
	public static ArrayList<FpAdderUnit> fp_adder_unit_pool = new ArrayList<FpAdderUnit>(); 
	public static ArrayList<FpDividerUnit> fp_divider_unit_pool = new ArrayList<FpDividerUnit>(); 
	public static ArrayList<FpMultiplierUnit> fp_multiplier_unit_pool = new ArrayList<FpMultiplierUnit>();

	public static FunctionalUnit assign_unit(Instruction inst){
		String type_of_unit = detect_type_of_unit(inst);
		
		FunctionalUnit unit = null;
		switch(type_of_unit){
			case "Adder":
				if(fp_adder_unit_pool.size() > 0)
					unit = fp_adder_unit_pool.remove(0);
				break;
			case "Divider":
				if(fp_divider_unit_pool.size() > 0)
					unit = fp_divider_unit_pool.remove(0);
				break;
			case "Multiplier":
				if(fp_multiplier_unit_pool.size() > 0)
					unit = fp_multiplier_unit_pool.remove(0);
				break;
			default:
				throw new Error("invalid functional unit type - " + type_of_unit);
		}
		
		return unit;
	}
	
	private static String detect_type_of_unit(Instruction inst) {
		String inst_class = inst.getClass().getSimpleName();
		String type_of_unit = null;
		
		switch(inst_class){
		case "SW": 
		case "LD": 
		case "SD": 
		case "LI": 
		case "LUI": 
		case "BEQ": 
		case "BNE": 
		case "AND": 
		case "ANDI": 
		case "OR": 
		case "ORI": 
		case "ADDD": 
		case "SUBD": 
			type_of_unit = "IntegerUnit";
			break;
		case "DADD": 
		case "DADDI": 
		case "DSUB": 
		case "DSUBI": 
			type_of_unit = "FpAdderUnit";
			break;
		case "MULD": 
			type_of_unit = "FpMultiplierUnit";
			break;
		case "DIVD": 
			type_of_unit = "FpDividerUnit";
			break;
		case "J": 
		case "HLT": 
			break;
		}

		return type_of_unit;
	}

}
