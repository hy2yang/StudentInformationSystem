package DAO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.fasterxml.jackson.core.JsonProcessingException;

import utilitises.JSONConverter;

public class CourseDAO {
	
	//private static Map<String, Course> courses = new HashMap<>();	
	
	private final static String TABLE_NAME="Courses";
	private static final Table table = DynamoDBClient.getTableByName(TABLE_NAME);
	
	
	public static String addCourse (String courseID, String name, String professorID) {
		
		Map<String, Object> item = new HashMap<>();
		item.put("courseID", courseID);
		item.put("professorID", professorID);
		item.put("name", name);	
		table.putItem(Item.fromMap(item));		
		
		return courseID;
	}
	
	/**
	 * @param courseID
	 * @return JSON of student record in table, or null if not exist
	 * @throws JsonProcessingException 
	 */
	public static String getCourseByID (String courseID) throws JsonProcessingException {
		return JSONConverter.object2JSON(table.getItem("courseID", courseID));
	}
		
	/**
	 * @param courseID
	 * @return
	 */
	public static DeleteItemOutcome deleteCourseByID (String courseID) {
		return table.deleteItem("courseID", courseID);
	}
	
	public static UpdateItemOutcome enrollStudentToCourse(String studentID, String courseID) {
		Set<String> set = new HashSet<>();
		set.add(studentID);
		
		UpdateItemSpec updateItemSpec = new UpdateItemSpec()
				.withPrimaryKey("courseID", courseID)
				.withUpdateExpression("add studentIDs :s")
				.withValueMap(new ValueMap()
						.withStringSet(":s", set)
						)
				.withReturnValues(ReturnValue.ALL_NEW);
		
		return table.updateItem(updateItemSpec);
	}	
	
	
	public static void main(String[] args) {	
		
		enrollStudentToCourse("S00000", "A1");
		enrollStudentToCourse("S00001", "A1");		
		enrollStudentToCourse("S00002", "A1");
		
	}
	
	
	/*
	public static void addAnnouncement (String courseID, String aID) {
		Set<String> set = new HashSet<>();
		set.add(aID);
		
		UpdateItemSpec updateItemSpec = new UpdateItemSpec()
				.withPrimaryKey("courseID", courseID)
				.withUpdateExpression("add announcements :a")
				.withValueMap(new ValueMap()
						.withStringSet(":a", set)
						)
				.withReturnValues(ReturnValue.ALL_NEW);
		
		table.updateItem(updateItemSpec);
	}
	*/
	
	/*
	
	course manipulation
	
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
	
	
	course student manipulation
	
	
	public static Set<String> getAllStudentsOfCourse(String cID){
		Set<String> IDs = courses.get(cID).getAllStudentIDs();
		Set<String> students = new HashSet<>();
		for (String i: IDs) {
			students.add(StudentDAO.getStudentByID(i));
		}
		return students;
	}	
	*/		

}
