package DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.Course;
import entity.Student;

public class CourseDAO {
	
	private static Map<String, Course> courses = new HashMap<>();	
	
	
	/*
	course manipulation
	*/
	public static Collection<Course> getAllCourses(){
		return courses.values();
	}
	
	public static Course getCourseByID (String cID) {
		return courses.get(cID);
	}
	
	public static boolean addCourse( Course c) {
		if (courses.containsValue(c)) return false;
		courses.put(c.getCourseID(),c);
		return true;
	}
	
	public static void deleteCourseByID (String cID) {
		courses.remove(cID);
	}	
	
	/*
	course student manipulation
	*/
	
	public static Set<Student> getAllStudentsOfCourse(String cID){
		Set<Integer> IDs = courses.get(cID).getAllStudentIDs();
		Set<Student> students = new HashSet<>();
		for (int i: IDs) {
			students.add(StudentDAO.getStudentByID(i));
		}
		return students;
	}			

}
