package DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import entity.Program;

public class ProgramDAO {

	private static Map<Integer, Program> programs = new HashMap<>();
	
	public static Program getProgramByID (int i) {
		return programs.get(i);
	}
	
	public static Collection<Program> getAllPrograms () {
		return programs.values();
	}
	
	public static boolean addProgram (Program p) {
		if ( programs.containsKey(p.getProgramID()) ) return false;
		programs.put(p.getProgramID(),p);
		return true;
	}
	
	public static void deleteProgramByID (int i) {
		programs.remove(i);
	}	
	
}
