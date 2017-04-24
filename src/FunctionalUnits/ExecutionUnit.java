package FunctionalUnits;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Instructions.Instruction;
import MIPS.MIPS;
import Managers.OutputManager;
import Stages.*;

public class ExecutionUnit {
	HashMap<FunctionalUnit, Integer> status = new HashMap<FunctionalUnit, Integer>();
	public static ArrayList<FpAdderUnit> fp_adder_unit_pool = new ArrayList<FpAdderUnit>(); 
	public static ArrayList<FpDividerUnit> fp_divider_unit_pool = new ArrayList<FpDividerUnit>(); 
	public static ArrayList<FpMultiplierUnit> fp_multiplier_unit_pool = new ArrayList<FpMultiplierUnit>();
	public static HashMap<Integer, FunctionalUnitData> busy_units = new HashMap<Integer, FunctionalUnitData>();

	public static Instruction execute_busy_units() throws Exception{
		Iterator<Entry<Integer, FunctionalUnitData>> itr = busy_units.entrySet().iterator();
		boolean passed_to_next_stage = false;
		Instruction inst = null;
		
		while (itr.hasNext()) {
	        Map.Entry<Integer, FunctionalUnitData> pair = (Map.Entry<Integer, FunctionalUnitData>)itr.next();
	        FunctionalUnitData fud = pair.getValue();
	        
	        if(fud.is_executing){ // marked to run	        			        
	            if(fud.remaining_latency != 0){
			        System.out.println("------execute " + fud.unit.getClass().getSimpleName() + " : " + fud.remaining_latency);
		        	fud.remaining_latency -= 1;
		        	busy_units.put(fud.output_index, fud);
		        }
	            if(fud.remaining_latency == 0 && !passed_to_next_stage){
		        	passed_to_next_stage = true;
		        	// TODO - move this to execute stage
		        	OutputManager.output_table.get(fud.output_index)[4] = MIPS.cycle;
		        	ExecuteStage.prev_inst_index = fud.inst_index;
					WriteStage.output_index = fud.output_index;
		        }
		    }
		}
		
		return inst;
	}

	public static boolean isUnitAvailable(Instruction inst) throws Exception{
		String type_of_unit = get_type_of_unit(inst);

		switch(type_of_unit){
			case "NoUnit":
				return true;
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

	public static void run_unit(int inst_index) {
		System.out.println("run_unit");
		FunctionalUnitData fud = busy_units.get(inst_index);
		fud.is_executing = true;
		busy_units.put(inst_index, fud);
	}
	public static void stop_unit(int inst_index) {
		System.out.println("stop_unit");
		FunctionalUnitData fud = busy_units.get(inst_index);
		fud.is_executing = false;
		busy_units.put(inst_index, fud);
	}
	
	public static void allocate_unit(Instruction inst, int inst_index, int output_index) throws Exception{
		String type_of_unit = get_type_of_unit(inst);
		FunctionalUnit unit = null;
		System.out.println("------allocate " + type_of_unit + " - " + output_index);
		
		switch(type_of_unit){
			case "NoUnit":
				return;
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
		unit.setBusy(true);
		FunctionalUnitData fud = new FunctionalUnitData(inst_index, unit, unit.latency, output_index);
		busy_units.put(output_index, fud);

		debug();
	}

	public static void deallocate_unit(FunctionalUnit unit) throws Exception{
    	System.out.println("------deallocate " + unit.getClass().getSimpleName());
 
    	unit.setBusy(false);
    	if(unit instanceof FpAdderUnit){
    		fp_adder_unit_pool.add((FpAdderUnit) unit);
    	}else if(unit instanceof FpDividerUnit){
    		fp_divider_unit_pool.add((FpDividerUnit) unit);		        		
    	}else if(unit instanceof FpMultiplierUnit){
    		fp_multiplier_unit_pool.add((FpMultiplierUnit) unit);		        		
    	}
		debug();
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
		case "AND": 
		case "ANDI": 
		case "OR": 
		case "ORI": 
		case "DADD": 
		case "DADDI": 
		case "DSUB": 
		case "DSUBI": 
			type_of_unit = "IntegerUnit";
			break;
		case "ADDD": 
		case "SUBD": 
			type_of_unit = "FpAdderUnit";
			break;
		case "MULD": 
			type_of_unit = "FpMultiplierUnit";
			break;
		case "DIVD": 
			type_of_unit = "FpDividerUnit";
			break;
		case "BEQ": 
		case "BNE": 
		case "J": 
		case "HLT": 
			type_of_unit = "NoUnit";
			break;
		default:
			throw new Error("No Functional Unit found for: " + inst_class);
		}

		return type_of_unit;
	}

	public static void debug() throws Exception {
		System.out.print("-----FU's------load: " + LoadUnit.i.isBusy());
		System.out.print(", integer:" + IntegerUnit.i.isBusy());
		System.out.print(", adder: " + ExecutionUnit.fp_adder_unit_pool.size());
		System.out.print(", divider: " + ExecutionUnit.fp_divider_unit_pool.size());
		System.out.println(", multiplier: " + ExecutionUnit.fp_multiplier_unit_pool.size());
	}
}
