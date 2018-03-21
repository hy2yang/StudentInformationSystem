package DAO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class AnnouncementDAO {
	
	private final static String TABLE_NAME="Announcements";
	private static int count = 0; 	
	private static final Table table = DynamoDBClient.getTableByName(TABLE_NAME);	
	
	public static String newAnnouncement (String courseID, String pID, String header, String body) {
		
		String id=aIDgen(count++);
		Map<String, Object> item = new HashMap<>();
		item.put("announcementID", id);
		item.put("courseID", courseID);
		item.put("professorID", pID);
		item.put("header", header);
		item.put("body", body);	
		item.put("time", LocalDateTime.now().toString());
		table.putItem(Item.fromMap(item));		
		
		return id;
	}
	
	private static String aIDgen(int i) {
		return String.format("%s%07d", "A" ,i);
	}
	
	public static void main(String[] args) {		
			newAnnouncement("A1", "fefe", "new test header", "this announcement has just been added to ddb");
	}

}
