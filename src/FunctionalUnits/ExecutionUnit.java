package FunctionalUnits;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Instructions.*;
import MIPS.MIPS;

public class ExecutionUnit {
	HashMap<FunctionalUnit, Integer> status = new HashMap<FunctionalUnit, Integer>();
	public static ArrayList<FpAdderUnit> fp_adder_unit_pool = new ArrayList<FpAdderUnit>();
	public static ArrayList<FpDividerUnit> fp_divider_unit_pool = new ArrayList<FpDividerUnit>();
	public static ArrayList<FpMultiplierUnit> fp_multiplier_unit_pool = new ArrayList<FpMultiplierUnit>();
	public static HashMap<Integer, FunctionalUnitData> busy_units = new HashMap<Integer, FunctionalUnitData>();

	public static ArrayList<FunctionalUnitData> execute_busy_units() throws Exception{
		Iterator<Entry<Integer, FunctionalUnitData>> itr = busy_units.entrySet().iterator();
		ArrayList<FunctionalUnitData> out = new ArrayList<FunctionalUnitData>();

		while (itr.hasNext()) {
	        Map.Entry<Integer, FunctionalUnitData> pair = itr.next();
	        FunctionalUnitData fud = pair.getValue();

	        if(fud.is_executing){ // marked to run
	            if(fud.remaining_latency != 0){
//			        System.out.println("FU execute " + fud.unit.getClass().getSimpleName() + " : " + fud.remaining_latency);
		        	fud.remaining_latency -= 1;
		        	pair.setValue(fud);
		        }

	            if(fud.remaining_latency == 0){
	            	out.add(fud);
		        }
		    }
		}

		return out;
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

	public static void run_unit(int gid) {
//		System.out.println("FU run_unit: " + gid);
		FunctionalUnitData fud = busy_units.get(gid);
		fud.is_executing = true;
		busy_units.put(gid, fud);
	}
	public static void stop_unit(int gid) {
		FunctionalUnitData fud = busy_units.get(gid);
//		System.out.println("stop_unit: " + gid);
		fud.is_executing = false;
		busy_units.put(gid, fud);
	}

	public static void allocate_unit(Instruction inst, int id, int gid) throws Exception{
		String type_of_unit = get_type_of_unit(inst);
		FunctionalUnit unit = null;
//		System.out.println("FU allocate " + type_of_unit + " - " + id);

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

		int latency = unit.latency;

		if(!MIPS.CACHING_ENABLED){
			if(inst instanceof LW || inst instanceof SW){
				latency = 1;
			}else if(inst instanceof LD || inst instanceof SD){
				latency = 2;
			}
		}

		FunctionalUnitData fud = new FunctionalUnitData(id, gid, unit, latency);
		busy_units.put(gid, fud);
	}

	public static void deallocate_unit(FunctionalUnitData fud) throws Exception{
//    	System.out.println("FU deallocate: " + fud.unit.getClass().getSimpleName() + " , " + fud.id);

    	fud.unit.setBusy(false);
    	if(fud.unit instanceof FpAdderUnit){
    		fp_adder_unit_pool.add((FpAdderUnit) fud.unit);
    	}else if(fud.unit instanceof FpDividerUnit){
    		fp_divider_unit_pool.add((FpDividerUnit) fud.unit);
    	}else if(fud.unit instanceof FpMultiplierUnit){
    		fp_multiplier_unit_pool.add((FpMultiplierUnit) fud.unit);
    	}

    	ExecutionUnit.busy_units.remove(fud.gid);
	}

	private static String get_type_of_unit(Instruction inst) {
		String inst_class = inst.getClass().getSimpleName();
		String type_of_unit = null;

		switch(inst_class){
		case "LW":
		case "LD":
		case "SW":
		case "SD":
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
		case "LI":
		case "LUI":
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
