package DAO;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import entity.Announcement;
import utilitises.DynamoDBClient;

public class AnnouncementDAO {
	
	private static DynamoDBMapper mapper = DynamoDBClient.getMapper();		

	public static String saveAnnouncement(Announcement a) {
		mapper.save(a);
		return a.getAnnouncementID();
	}		
	
	public static Announcement getAnnouncementByID (String aID){
		return mapper.load(Announcement.class, aID);
	}
		
	public static List<Announcement> getAnnouncementsOfCourse (String cid){
		return mapper.query(Announcement.class
				,new DynamoDBQueryExpression<Announcement>()
				.addExpressionAttributeNamesEntry("courseID", cid));
	}	
	
	public static void main(String[] args) {		
			saveAnnouncement(new Announcement("A1", "fefe",
					"new test header", "this announcement has just been added to ddb"));
	}

}
