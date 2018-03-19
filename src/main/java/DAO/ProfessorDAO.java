package DAO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

public class ProfessorDAO {

	private final static String TABLE_NAME="Professors";
	private static int count = 0; 	
	private static final Table table = DynamoDBClient.getTableByName(TABLE_NAME);
	
	public static String addProfessor (String sName, String email) {
		
		String id=pIDgen(count++);
		Map<String, Object> item = new HashMap<>();
		item.put("professorID", id);
		item.put("name", sName);
		item.put("email", email);	
		table.putItem(Item.fromMap(item));		
		
		return id;
	}
	
	private static String pIDgen( int i) {
		return String.format("%s%05d", "P" ,i);
	}
	
	
	public static void teachCourse(String pID, String cID) {
		Set<String> set = new HashSet<>();
		set.add(cID);
		
		UpdateItemSpec updateItemSpec = new UpdateItemSpec()
				.withPrimaryKey("professorID", pID)
				.withUpdateExpression("add courseIDs :c")
				.withValueMap(new ValueMap()
						.withStringSet(":c", set)
						)
				.withReturnValues(ReturnValue.ALL_NEW);
		
		table.updateItem(updateItemSpec);
	}	
	
}
