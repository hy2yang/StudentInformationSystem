package DAO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

public class StudentDAO {
	
	//private static Map<String, Student> students = new HashMap<>();
	
	private final static String TABLE_NAME="Students";
	private static int count = 0; 	
	private static final Table table = DynamoDBClient.getTableByName(TABLE_NAME);
	
	
	public static String addStudent (String sName, String email) {
		/*
		Student s= new Student(sName, email);
		students.put(s.getID(),s);
		*/
		String id=sIDgen(count++);
		Map<String, Object> item = new HashMap<>();
		item.put("studentID", id);
		item.put("name", sName);
		item.put("email", email);	
		table.putItem(Item.fromMap(item));		
		
		return id;
	}
	
	private static String sIDgen( int i) {
		return String.format("%s%05d", "S" ,i);
	}	
	
	public static Item getStudentByID (String sID){
		return table.getItem("studentID", sID);
	}
		
	public static DeleteItemOutcome deleteStudentByID (String sID) {
		return table.deleteItem("studentID", sID);
	}
	
	public static void enrollStudentToCourse(String sID, String cID) {
		Set<String> set = new HashSet<>();
		set.add(cID);
		
		UpdateItemSpec updateItemSpec = new UpdateItemSpec()
				.withPrimaryKey("studentID", sID)
				.withUpdateExpression("add courseIDs :c")
				.withValueMap(new ValueMap()
						.withStringSet(":c", set)
						)
				.withReturnValues(ReturnValue.ALL_NEW);
		
		table.updateItem(updateItemSpec);
	}	
	
	
	/*
	public static Set<Course> getAllCoursesOfStudent (String sID) {
		Set<String> IDs=students.get(sID).getCourseIDs();
		Set<Course> courses=new HashSet<>();
		for (String i: IDs) {
			courses.add(CourseDAO.getCourseByID(i));
		}
		return courses;
	}
	*/
	
	/*
	public static Collection<Student> getAllStudents () {		
		return students.values();
	}
	*/
	
	
	public static void main(String[] args) {
		
		addStudent("tsdasdw", "tsdasdw@cecef.com");
		addStudent("asdwd", "asdwd@cecef.com");
		addStudent("oiuhgtt", "oiuhgtt@cecef.com");
		
	}
	
}
