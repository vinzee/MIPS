package Managers;

import java.util.ArrayList;
import java.util.HashMap;
import FunctionalUnits.*;

public class FunctionalUnitManager {
	HashMap<FunctionalUnit, Integer> status = new HashMap<FunctionalUnit, Integer>();
	
//	public static ArrayList<IntegerUnit> integer_unit_pool = new ArrayList<IntegerUnit>(); 
	public static ArrayList<FpAdderUnit> fp_adder_unit_pool = new ArrayList<FpAdderUnit>(); 
	public static ArrayList<FpDividerUnit> fp_divider_unit_pool = new ArrayList<FpDividerUnit>(); 
	public static ArrayList<FpMultiplierUnit> fp_multiplier_unit_pool = new ArrayList<FpMultiplierUnit>();

	public static FunctionalUnit assign_unit(String type_of_unit){
		FunctionalUnit unit = null;
		switch(type_of_unit){
			case "adder":
				if(fp_adder_unit_pool.size() > 0)
					unit = fp_adder_unit_pool.remove(0);
				break;
			case "divider":
				if(fp_divider_unit_pool.size() > 0)
					unit = fp_divider_unit_pool.remove(0);
				break;
			case "multiplier":
				if(fp_multiplier_unit_pool.size() > 0)
					unit = fp_multiplier_unit_pool.remove(0);
				break;
			default:
				throw new Error("invalid functional unit type - " + type_of_unit);
		}
		
		return unit;
	}
}
