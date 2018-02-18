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
	/*static {
		courses.put("this0", new Course("this0"));
		courses.put("is1", new Course("is1"));
		courses.put("test2", new Course("test2"));
		
		Lecture[] samples = new Lecture[3];
		for (Lecture l: samples) {
			l.putMaterial("some picture", "picture URL");
			l.putMaterial("some video", "video URL");
			for (int i=0;i<2;++i) {
				l.addNote("note No."+i);
			}
		}
		
		for (Course i: courses.values()) {
			for (int j=0;j<samples.length;++j) i.addLecture(samples[j]);
		}
	}*/
	
	
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
