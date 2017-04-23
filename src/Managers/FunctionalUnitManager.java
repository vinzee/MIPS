package Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import FunctionalUnits.*;
import Instructions.Instruction;
import MIPS.MIPS;

public class FunctionalUnitManager {
	HashMap<FunctionalUnit, Integer> status = new HashMap<FunctionalUnit, Integer>();
	public static ArrayList<FpAdderUnit> fp_adder_unit_pool = new ArrayList<FpAdderUnit>(); 
	public static ArrayList<FpDividerUnit> fp_divider_unit_pool = new ArrayList<FpDividerUnit>(); 
	public static ArrayList<FpMultiplierUnit> fp_multiplier_unit_pool = new ArrayList<FpMultiplierUnit>();
	public static HashMap<FunctionalUnit, Integer[]> busy_units = new HashMap<FunctionalUnit, Integer[]>();

	public static Instruction execute_busy_units(){
		Iterator<Entry<FunctionalUnit, Integer[]>> itr = busy_units.entrySet().iterator();
		boolean passed_to_next_stage = false;
		Instruction inst = null;
		
		while (itr.hasNext()) {
	        Map.Entry<FunctionalUnit, Integer[]> pair = (Map.Entry<FunctionalUnit, Integer[]>)itr.next();
	        FunctionalUnit unit = pair.getKey();
	        Integer[] inst_data = pair.getValue();
	        
	        if(inst_data[1] != 0){
	        	inst_data[1] -= 1;
	        	pair.setValue(inst_data);
	        }
	        
	        System.out.println("execute_busy_units - " + unit.getClass().getSimpleName() + " : " + pair.getValue()[1]);

	        if(inst_data[1] == 0 && passed_to_next_stage){
	        	passed_to_next_stage = true;
	        	inst = MIPS.instructions.get(inst_data[0]);
	        	
	        	unit.setBusy(false);
	        	if(unit instanceof FpAdderUnit){
	        		fp_adder_unit_pool.add((FpAdderUnit) unit);
	        	}else if(unit instanceof FpDividerUnit){
	        		fp_divider_unit_pool.add((FpDividerUnit) unit);		        		
	        	}else if(unit instanceof FpMultiplierUnit){
	        		fp_multiplier_unit_pool.add((FpMultiplierUnit) unit);		        		
	        	}
	        	
	        	itr.remove();	        	
	        }
		}
		
		return inst;
	}

	public static boolean isUnitAvailable(Instruction inst) throws Exception{
		String type_of_unit = get_type_of_unit(inst);
		debug();

		switch(type_of_unit){
			case "LoadUnit":
				return !LoadUnit.i.isBusy();
			case "IntegerUnit":
				return !IntegerUnit.i.isBusy();
			case "FpAdderUnit":
				return fp_adder_unit_pool.size() > 0;
			case "FpDividerUnit":
				return fp_divider_unit_pool.size() > 0;
			case "FpMultiplierUnit":
				return fp_multiplier_unit_pool.size() > 0;
			default:
				throw new Error("invalid functional unit type - " + type_of_unit);
		}
	}
	
	public static FunctionalUnit assign_unit(Instruction inst, int inst_index) throws Exception{
		String type_of_unit = get_type_of_unit(inst);
		FunctionalUnit unit = null;
//		System.out.println("assign_unit - " + type_of_unit);
		switch(type_of_unit){
			case "LoadUnit":
				unit = LoadUnit.i;
				break;
			case "IntegerUnit":
				unit = IntegerUnit.i;
				break;
			case "FpAdderUnit":
				unit = fp_adder_unit_pool.remove(0);
				break;
			case "FpDividerUnit":
				unit = fp_divider_unit_pool.remove(0);
				break;
			case "FpMultiplierUnit":
				unit = fp_multiplier_unit_pool.remove(0);
				break;
		}		
		
		busy_units.put(unit, new Integer[]{inst_index, unit.latency});
		
		return unit;
	}
	
	private static String get_type_of_unit(Instruction inst) {
		String inst_class = inst.getClass().getSimpleName();
		String type_of_unit = null;
		
		switch(inst_class){
		case "SW": 
		case "LD": 
		case "SD": 
		case "LI": 
		case "LUI": 
			type_of_unit = "LoadUnit";
			break;
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

	public static void debug() throws Exception {
		System.out.print("-----FU's------load: " + LoadUnit.i.isBusy());
		System.out.print(", integer:" + IntegerUnit.i.isBusy());
		System.out.print(", adder: " + FunctionalUnitManager.fp_adder_unit_pool.size());
		System.out.print(", divider: " + FunctionalUnitManager.fp_divider_unit_pool.size());
		System.out.println(", multiplier: " + FunctionalUnitManager.fp_multiplier_unit_pool.size());
	}

}
