package DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import entity.Lecture;
import utilitises.DynamoDBClient;

public class LectureDAO {

	private static DynamoDBMapper mapper = DynamoDBClient.getMapper();
	
	public static Collection<Lecture> getAllLectures () {
		return mapper.scan(Lecture.class, new DynamoDBScanExpression());
	}
	
	public static String saveLecture (Lecture l) {
		mapper.save(l);
		return l.getLectureID();
	}	
	
	public static Lecture getLectureByID (String lectureID){
		return mapper.load(Lecture.class, lectureID);
	}
		
	public static void deleteLectureByID (String lectureID) {
		Lecture temp = new Lecture(lectureID);
		mapper.delete(temp);
	}
	
	public static List<Lecture> getLectureByID (List<String> ids) {
		List<Lecture> res = new ArrayList<>(ids.size());
		for (String i : ids) {
			res.add(getLectureByID(i));
		}
		return res;
	}
	
}
