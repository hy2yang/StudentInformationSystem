package DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import entity.Student;

public class StudentDAO {
	
	private static Map<Integer, Student> students = new HashMap<>();
	static {
		students.put(0, new Student(0,"student0",2));
		students.put(1, new Student(1,"student1",1));
		students.put(2, new Student(2,"student2",0));
	}
	
	public static Collection<Student> getAllStudents () {
		return students.values();
	}
	
	public static Student getStudentByID (int i) {
		return students.get(i);
	}
	
	public static boolean addStudent (Student s) {
		if ( students.containsKey(s.getID()) ) return false;
		students.put(s.getID(),s);
		return true;
	}
	
	public static void deleteStudentByID (int i) {
		students.remove(i);
	} 

	
}
