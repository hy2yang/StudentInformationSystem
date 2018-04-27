package DAO;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import entity.Student;
import utilitises.DynamoDBClient;

public class BillDAO {
	
	private final static String TABLE_NAME="Bills";
	private static final Table table = DynamoDBClient.getTableByName(TABLE_NAME);
	private static float PRICE_PER_COURSE = 1000;
	
	public static float getPRICE_PER_COURSE() {
		return PRICE_PER_COURSE;
	}

	public static void setPRICE_PER_COURSE(float p) {
		PRICE_PER_COURSE = p;
	}
	
	public static String generateBill(String sID) {
		Student s = StudentDAO.getStudentByID(sID);
		int courseNum = s.getCourseIDs().size();
		String timestamp = ZonedDateTime.now().toString();
		
		Map<String, Object> itemMap = new HashMap<>();
		itemMap.put("studentID", sID);
		itemMap.put("timestamp", timestamp);
		itemMap.put("registered courses", courseNum);
		itemMap.put("billing total", courseNum*PRICE_PER_COURSE);		
		table.putItem(Item.fromMap(itemMap));
		
		return table.getItem("studentID", sID, "timestamp", timestamp).toJSON();
	}
	
	public static void main(String[] args) {
		generateBill("S1248234350");
	}
	
}
