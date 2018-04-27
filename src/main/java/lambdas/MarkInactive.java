package lambdas;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class MarkInactive implements RequestHandler<String, String>{
	
	private static Table t = new DynamoDB(
			AmazonDynamoDBClientBuilder
			.standard()
			.withCredentials(new EnvironmentVariableCredentialsProvider())
			.withRegion("us-west-2")
			.build()
			)
			.getTable("Students");	
	
	@Override
	public String handleRequest(String json, Context context) {
		Item student = Item.fromJSON(json);
		String sID = student.getString("studentID");
		t.updateItem(
				new UpdateItemSpec()
				.withPrimaryKey("studentID", sID)
				.withUpdateExpression("set active = :a")
				.withValueMap(new ValueMap().withNumber(":a", 0))
				);
		return t.getItem("studentID", sID).toJSON();
	}

}
