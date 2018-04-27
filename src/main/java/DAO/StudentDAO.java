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
	
	public static String addNewStudent (Student s) {	
		s.setStudentID(sIDgen(s.hashCode()));
		mapper.save(s);
		return s.getStudentID();
	}
	
	private static String sIDgen( int i) {
		return String.format("%s%010d", "S" ,i);
	}	
	
	public static String saveStudent (Student s) {	
		mapper.save(s);
		return s.getStudentID();
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
		temp.setStudentID(sID);
		mapper.delete(temp);
	}
	
	public static void main(String[] args) {
		Student s = new Student("hoth", "ofepi@vgg.com");
		System.out.println(s.getStudentID());
		saveStudent(s);
		System.out.println(s.getStudentID());
	}
	
}
