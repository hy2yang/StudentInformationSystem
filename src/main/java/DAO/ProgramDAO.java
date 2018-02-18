package DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.Course;
import entity.Program;

public class ProgramDAO {

	private static Map<Integer, Program> programs = new HashMap<>();
	static {
		Program s0= new Program(0,"ptest0");
		s0.addCourseByID("this0");
		programs.put(0, s0);
		
		
		Program s1= new Program(1,"ptest1");
		s1.addCourseByID("is1");
		programs.put(1, s1);
		
		Program s2= new Program(2,"ptest2");
		s2.addCourseByID("test2");
		programs.put(2, s2);	
		
	}
	
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
	
	public static Set<Course> getCoursesOfProgram(int pID) {
		Set<String> IDs=programs.get(pID).getCourseIDs();
		Set<Course> courses=new HashSet<>();
		for (String i: IDs) {
			courses.add(CourseDAO.getCourseByID(i));
		}
		return courses;
	}
}
