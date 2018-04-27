package DAO;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import entity.Course;
import entity.Student;
import utilitises.DynamoDBClient;

public class CrossDAO {
	
	private static DynamoDBMapper mapper = DynamoDBClient.getMapper();	
	
	/*
	public static Set<Course> getCoursesOfProgram(String pID) {
		Set<String> IDs=mapper.load(Program.class, pID).getCourseIDs();
		Set<Course> courses=new HashSet<>();
		for (String i: IDs) {
			courses.add(CourseDAO.getCourseByID(i));
		}
		return courses;
	}
	*/
	
	public static void studentEnrollCourse(String sID, String cID) {
		Student s = StudentDAO.getStudentByID(sID);
		Course c = CourseDAO.getCourseByID(cID);
		s.enrollCourse(cID);
		c.enrollStudent(sID);
		mapper.save(s);
		mapper.save(c);		
	}
	
	public static void studentDropCourse(String sID, String cID) {		
		Student s = StudentDAO.getStudentByID(sID);
		Course c = CourseDAO.getCourseByID(cID);
		s.dropCourse(cID);
		c.dropStudent(sID);
		mapper.save(s);
		mapper.save(c);
	}
	
}
