package DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import entity.Student;
import utilitises.DynamoDBClient;

public class StudentDAO {
	
	private static DynamoDBMapper mapper = DynamoDBClient.getMapper();
	
	public static String addStudent (Student s) {
		String id;
		do {
			id=sIDgen((int)Math.random());
		}
		while ( mapper.load(Student.class, id)!=null);
		s.setID(id);
		mapper.save(s);
		return id;
	}
	
	private static String sIDgen( int i) {
		return String.format("%s%05d", "S" ,i);
	}	
	
	public static List<Student> getAllStudents(){
		return mapper.scan(Student.class, new DynamoDBScanExpression());
	}
	
	public static Student getStudentByID (String sID){
		return mapper.load(Student.class, sID);
	}
	
	public static List<Student> getStudentByID (Set<String> ids) {
		List<Student> res = new ArrayList<>(ids.size());
		for (String i : ids) {
			res.add(getStudentByID(i));
		}
		return res;
	}
		
	public static void deleteStudentByID (String sID) {
		Student temp = new Student();
		temp.setID(sID);
		mapper.delete(temp);
	}
	/*
	public static void enrollStudentToCourse(String sID, String cID) {		
		mapper.load(Student.class, sID).enrollCourse(cID);
	}	
	*/	
	
	public static void main(String[] args) {
		
		addStudent(new Student("tsdasdw", "tsdasdw@cecef.com"));
		addStudent(new Student("asdwd", "asdwd@cecef.com"));
		addStudent(new Student("oiuhgtt", "oiuhgtt@cecef.com"));
		
	}
	
}
