package DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import entity.Program;

public class ProgramDAO {

	private static Map<Integer, Program> programs = new HashMap<>();
	
	public static Collection<Program> getAllPrograms () {
		return programs.values();
	}
	
	public static Program getProgramByID (int pID) {
		return programs.get(pID);
	}
	
	public static boolean addProgram (Program p) {
		if ( programs.containsKey(p.getProgramID()) ) return false;
		programs.put(p.getProgramID(),p);
		return true;
	}
	
	public static void deleteProgramByID (int pID) {
		programs.remove(pID);
	}
	
	/*
	public static Set<Course> getCoursesOfProgram(int pID) {
		Set<String> IDs=programs.get(pID).getCourseIDs();
		Set<Course> courses=new HashSet<>();
		for (String i: IDs) {
			courses.add(CourseDAO.getCourseByID(i));
		}
		return courses;
	}
	*/
}
