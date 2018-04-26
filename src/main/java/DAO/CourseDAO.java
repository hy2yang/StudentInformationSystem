package DAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import entity.Course;
import utilitises.DynamoDBClient;

public class CourseDAO {
	
	private static DynamoDBMapper mapper = DynamoDBClient.getMapper();
	
	public static List<Course> getAllCourses () {
		return mapper.scan(Course.class, new DynamoDBScanExpression());
	}
	
	public static String saveCourse (Course c) {		
		if ( mapper.load(Course.class, c.getCourseID())!=null ) return null;
		mapper.save(c);
		return c.getCourseID();
	}	
	
	public static Course getCourseByID (String courseID){
		return mapper.load(Course.class, courseID);
	}
	
	public static Set<Course> getCourseByID (Set<String> ids){
		Set<Course> res = new HashSet<>();
		for (String id :ids) {
			res.add(CourseDAO.getCourseByID(id));
		}
		return res;
	}
		
	public static void deleteCourseByID (String pID) {
		Course temp = new Course(pID, "to-be-deleted", "to-be-deleted");
		mapper.delete(temp);
	}

}
