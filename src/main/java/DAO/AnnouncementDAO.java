package DAO;

import java.time.LocalDateTime;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import entity.Announcement;
import utilitises.DynamoDBClient;

public class AnnouncementDAO {
	
	private static DynamoDBMapper mapper = DynamoDBClient.getMapper();	
	
	private static String aIDgen(int i) {
		return String.format("%s%010d", "A" ,i);
	}
	
	public static String postAnnouncement (Announcement toAdd) {	
		toAdd.setTime(LocalDateTime.now().toString());
		String id=aIDgen(toAdd.hashCode());		
		toAdd.setAnnouncementID(id);		
		mapper.save(toAdd);		
		return id;
	}
	
	public static List<Announcement> getAnnouncementsOfCourse (String cid){
		return mapper.query(Announcement.class
				,new DynamoDBQueryExpression<Announcement>()
				.addExpressionAttributeNamesEntry("courseID", cid));
	}
	
	
	
	public static void main(String[] args) {		
			postAnnouncement(new Announcement("A1", "fefe",
					"new test header", "this announcement has just been added to ddb"));
	}

}
