package DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.Course;
import entity.Student;

public class StudentDAO {
	
	private static Map<Integer, Student> students = new HashMap<>();
	
	public static Collection<Student> getAllStudents () {
		return students.values();
	}
	
	public static Student getStudentByID (int sID) {
		return students.get(sID);
	}
	
	public static boolean addStudent (Student s) {
		if ( students.containsKey(s.getID()) ) return false;
		students.put(s.getID(),s);
		return true;
	}
	
	public static void deleteStudentByID (int sID) {
		students.remove(sID);
	} 

	public static Set<Course> getAllCoursesOfStudent (int sID) {
		Set<String> IDs=students.get(sID).getCourseIDs();
		Set<Course> courses=new HashSet<>();
		for (String i: IDs) {
			courses.add(CourseDAO.getCourseByID(i));
		}
		return courses;
	}
	
}
